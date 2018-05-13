using System;
using System.Collections.Generic;
using System.Linq;

namespace SO2
{
	abstract class SSTF
	{
		private static int FindClosest(int position, List<Request> list)
		{
			int minDist = int.MaxValue;
			int minIdx = 0;
			for (int i = 0; i < list.Count; i++)
			{
				if (Math.Abs(list[i].Position - position) < minDist)
				{
					minDist = Math.Abs(list[i].Position - position);
					minIdx = i;
				}
			}
			return minIdx;
		}

		private static void UpdateQueue1(int time, Queue<Request> dataset, List<Request> que, List<Request> prioQue)
		{
			while (dataset.Any() && dataset.Peek().EnterTime <= time)
			{
				if (dataset.Peek().HasDeadline())
				{
					prioQue.Add(dataset.Dequeue());
					prioQue.Sort();
				}
				else
					que.Add(dataset.Dequeue());
			}
		}

		public static double SimulateEDF(ICollection<Request> requests)
		{
			int n = requests.Count;
			List<Request> que = new List<Request>();
			Queue<Request> dataset = new Queue<Request>(requests);
			List<Request> prioQue = new List<Request>();
			int headPosition = 0;
			int delay = 0;
			Request target = null;
			int moves = 0;
			for (int time = 0; dataset.Any() || que.Any() || prioQue.Any() || target != null; time++)
			{
				UpdateQueue1(time, dataset, que, prioQue);
				if (target == null && prioQue.Any())
				{
					target = prioQue.First();
					prioQue.RemoveAt(0);
				}
				else if (target != null && !target.HasDeadline() && prioQue.Any())
				{
					que.Add(target);
					target = prioQue.First();
					prioQue.RemoveAt(0);
				}
				if (que.Any() && (target == null || !target.HasDeadline()))
				{
					int minIdx = FindClosest(headPosition, que);
					if (target == null || Math.Abs(headPosition - target.Position) > Math.Abs(headPosition - que[minIdx].Position))
					{
						if (target != null)
							que.Add(target);
						target = que[minIdx];
						que.RemoveAt(minIdx);

						if (headPosition == target.Position)
						{
							delay += target.Exec(time);
							target = null;
						}
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
			return (double)moves;
		}


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
			List<Request> que = new List<Request>();
			Queue<Request> dataset = new Queue<Request>(requests);
			List<Request> prioQue = new List<Request>();
			int headPosition = 0;
			int delay = 0;
			DIRECTION direction = DIRECTION.RIGHT;
			Request target = null;
			int moves = 0;
			for (int time = 0; dataset.Any() || que.Any() || prioQue.Any() || target != null; time++)
			{
				UpdateQueue(time, dataset, que, prioQue);
				if (prioQue.Any())
				{
					if (target != null)
					{
						que.Add(target);
						target = null;
					}
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
					continue;
				}
				if (que.Any())
				{
					int minIdx = FindClosest(headPosition, que);
					if (target == null || Math.Abs(headPosition - target.Position) > Math.Abs(headPosition - que[minIdx].Position))
					{
						if (target != null)
							que.Add(target);
						target = que[minIdx];
						que.RemoveAt(minIdx);

						if (headPosition == target.Position)
						{
							delay += target.Exec(time);
							target = null;
						}
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
