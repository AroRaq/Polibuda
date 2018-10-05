using System;
using System.Collections.Generic;
using System.Linq;

namespace SO2
{
	abstract class C_SCAN
	{
		private static void UpdateQueue(int time, Queue<Request> dataset, List<Request> que, List<Request> prioQue)
		{
			while (dataset.Any() && dataset.Peek().EnterTime <= time)
			{
				if (dataset.Peek().HasDeadline())
					prioQue.Add(dataset.Dequeue());
				else
					que.Add(dataset.Dequeue());
			}
		}

		public static double SimulateFD(ICollection<Request> requests)
		{
			int n = requests.Count;
			Queue<Request> dataset = new Queue<Request>(requests);
			List<Request> que = new List<Request>();
			List<Request> prioQue = new List<Request>();
			int headPosition = 0;
			int delay = 0;
			DIRECTION direction = DIRECTION.RIGHT;
			int moves = 0;
			for (int time = 0; dataset.Any() || que.Any() || prioQue.Any(); time++)
			{
				UpdateQueue(time, dataset, que, prioQue);

				if (prioQue.Any())
				{
					if (Methods.Find(direction, prioQue, headPosition))
						headPosition += (int)direction;

					else
					{
						direction = direction == DIRECTION.LEFT ? DIRECTION.RIGHT : DIRECTION.LEFT;
						headPosition += (int)direction;
					}
					moves++;
					foreach (Request r in prioQue)
					{
						if (r.Position == headPosition)
						{
							delay += r.Exec(time);
							prioQue.Remove(r);
							break;
						}
					}
				}
				else
				{
					headPosition += (int)direction;
					moves++;
					foreach (Request r in que)
					{
						if (r.Position == headPosition)
						{
							delay += r.Exec(time);
							que.Remove(r);
							break;
						}
					}
					if (headPosition == 0 || headPosition == Program.DISK_SIZE)
					{
						headPosition = headPosition == 0 ? Program.DISK_SIZE : 0;
					}
				}
			}
			return moves;
			//return (double)delay / n;
		}





		private static void UpdateQueue(int time, Queue<Request> dataset, List<Request> que, Queue<Request> prioQue)
		{
			while (dataset.Any() && dataset.Peek().EnterTime <= time)
			{
				if (dataset.Peek().HasDeadline())
					prioQue.Enqueue(dataset.Dequeue());
				else
					que.Add(dataset.Dequeue());
			}
		}

		public static double SimulateEDF(ICollection<Request> requests)
		{
			int n = requests.Count;
			Queue<Request> dataset = new Queue<Request>(requests);
			List<Request> que = new List<Request>();
			Queue<Request> prioQue = new Queue<Request>();
			int headPosition = 0;
			int delay = 0;
			DIRECTION direction = DIRECTION.RIGHT;
			Request target = null;
			int moves = 0;
			for (int time = 0; dataset.Any() || que.Any() || prioQue.Any(); time++)
			{
				UpdateQueue(time, dataset, que, prioQue);
				if (prioQue.Any() && target == null)
				{
					target = prioQue.Dequeue();
				}
				if (target != null)
				{
					headPosition += Math.Sign(target.Position - headPosition);
					moves++;
					if (target.Position == headPosition)
					{
						delay += target.Exec(time);
						target = null;
					}
					continue;
				}
				headPosition += (int)direction;
				moves++;
				foreach (Request r in que)
				{
					if (r.Position == headPosition)
					{
						delay += r.Exec(time);
						que.Remove(r);
						break;
					}
				}
				if (headPosition == 0 || headPosition == Program.DISK_SIZE)
				{
					headPosition = headPosition == 0 ? Program.DISK_SIZE : 0;
				}
			}
			return moves;
			//return (double)delay / n;
		}

	}
}
