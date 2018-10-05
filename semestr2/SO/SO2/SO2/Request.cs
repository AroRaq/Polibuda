using System;

namespace SO2
{
	public class Request : IComparable
	{
		public int EnterTime { get; }
		public int Position { get; }
		public int Deadline { get; }
		
		public Request(int EnterTime_, int Position_, int Deadline_ = -1)
		{
			EnterTime = EnterTime_;
			Position = Position_;
			Deadline = Deadline_ == -1 ? -1 : EnterTime + Deadline_;
		}
		public Request(Request copyFrom)
		{
			EnterTime = copyFrom.EnterTime;
			Position = copyFrom.Position;
			Deadline = copyFrom.Deadline;
		}
		public int Exec(int time)
		{
			return time - EnterTime;
		}
		public bool HasDeadline()
		{
			return Deadline != -1;
		}

		public int CompareTo(object obj)
		{
			if (obj is Request)
			{
				Request r = obj as Request;
				return (Deadline).CompareTo(r.Deadline);
			}
			throw new NotImplementedException();
		}
	}
}
