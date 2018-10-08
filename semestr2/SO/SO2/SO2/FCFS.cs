using System;
using System.Collections.Generic;
using System.Linq;

namespace SO2
{
	abstract class FCFS
	{
		private static void UpdateQueue1(int time, Queue<Request> dataset, LinkedList<Request> que, List<Request> prioQue)
		{
			while (dataset.Any() && dataset.Peek().EnterTime <= time)
			{
				if (dataset.Peek().HasDeadline())
				{
					prioQue.Add(dataset.Dequeue());
					prioQue.Sort();
				}
				else
					que.AddLast(dataset.Dequeue());
			}
		}

		public static double SimulateEDF(ICollection<Request> requests)
		{
			int n = requests.Count;
			LinkedList<Request> que = new LinkedList<Request>();
			Queue<Request> dataset = new Queue<Request>(requests);
			List<Request> prioQue = new List<Request>();
			int headPosition = 0;
			int delay = 0;
			Request target = null;
			int moves = 0;
			for (int time = 0; dataset.Any() || que.Any() || prioQue.Any() || target != null; time++)
			{
				UpdateQueue1(time, dataset, que, prioQue);
				if (target == null && (que.Any() || prioQue.Any()))
				{
					if (prioQue.Any())
					{
						target = prioQue.First();
						prioQue.RemoveAt(0);
					}
					else
					{
						target = que.First();
						que.RemoveFirst();
					}
				}
				if (target != null)
				{
					if (!target.HasDeadline() && prioQue.Any())
					{
						que.AddFirst(target);
						target = prioQue.First();
						prioQue.RemoveAt(0);
					}
					headPosition += Math.Sign(target.Position - headPosition);
					moves++;
					if (target.Position == headPosition)
					{
						delay += target.Exec(time);
						target = null;
					}
				}
			}
			return (double)moves;
		}





		private static void UpdateQueue(int time, Queue<Request> dataset, LinkedList<Request> que, List<Request> prioQue)
		{
			while (dataset.Any() && dataset.Peek().EnterTime <= time)
			{
				if (dataset.Peek().HasDeadline())
					prioQue.Add(dataset.Dequeue());
				else
					que.AddLast(dataset.Dequeue());
			}
		}
		public static double SimulateFD(ICollection<Request> requests)
		{
			int n = requests.Count;
			LinkedList<Request>que = new LinkedList<Request>();
			Queue<Request>dataset = new Queue<Request>(requests);
			List<Request>prioQue = new List<Request>();
			DIRECTION direction = DIRECTION.RIGHT;
			int headPosition = 0;
			int delay = 0;
			Request target = null;
			int moves = 0;
			for (int time = 0; dataset.Any() || que.Any() || prioQue.Any() || target != null; time++)
			{
				UpdateQueue(time, dataset, que, prioQue);
				if (prioQue.Any())
				{
					if (!Methods.Find(direction, prioQue, headPosition))
						direction = direction == DIRECTION.LEFT ? DIRECTION.RIGHT : DIRECTION.LEFT;
					
					headPosition += (int)direction;
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
					continue;
				}
				else if (target == null && que.Any())
				{
					target = que.First();
					que.RemoveFirst();
					
					if (target.Position == headPosition)
					{
						delay += target.Exec(time);
						target = null;
					}
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
				}
			}
			return moves;
			//return (double)delay / n;
		}
	}
}
