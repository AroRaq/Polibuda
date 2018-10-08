using System.Collections.Generic;

namespace SO2
{
	public enum DIRECTION
	{
		LEFT = -1,
		RIGHT = 1
	}

	public static class Methods
	{

		public static bool Find(DIRECTION dir, List<Request> list, int position)
		{
			foreach (Request r in list)
			{
				if ((dir == DIRECTION.LEFT && r.Position < position) || (dir == DIRECTION.RIGHT && r.Position > position))
				{
					return true;
				}
			}
			return false;
		}
	}
}
