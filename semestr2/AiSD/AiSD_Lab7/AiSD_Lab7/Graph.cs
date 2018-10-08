using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AiSD_Lab7
{
	class Graph<T>
	{
		internal class Node<E>
		{
			internal E Key;
			internal List<Node<E>> Connections = new List<Node<E>>();
			internal Node<E> Predecessor = null;

			internal Node(E key)
			{
				Key = key;
			}
		}
		Dictionary<T, Node<T>> Vertices = new Dictionary<T, Node<T>>();

		public void Add(T Vertex)
		{
			if (!Vertices.ContainsKey(Vertex))
				Vertices.Add(Vertex, new Node<T>(Vertex));
		}

		public void Connect(T v1, T v2)
		{
			Add(v1);
			Add(v2);
			Vertices[v1].Connections.Add(Vertices[v2]);
		}

		public bool FindCycle() //DFS
		{
			foreach (KeyValuePair<T, Node<T>> entry in Vertices)
			{
				entry.Value.Predecessor = null;
			}
			Stack<Tuple<Node<T>, Node<T>>> stack = new Stack<Tuple<Node<T>, Node<T>>>();

			bool again = true;
			while (again)
			{
				again = false;
				foreach (KeyValuePair<T, Node<T>> entry in Vertices)
				{
					if (entry.Value.Predecessor == null)
					{
						entry.Value.Predecessor = entry.Value;
						stack.Push(new Tuple<Node<T>, Node<T>>(entry.Value, entry.Value));
						again = true;
						break;
					}
				}
				while (stack.Any())
				{
					Tuple<Node<T>, Node<T>> current = stack.Pop();
					current.Item1.Predecessor = current.Item2;
					//System.Console.Out.WriteLine(current.Key);
					foreach (Node<T> n in current.Item1.Connections)
					{
						if (current.Item1.Predecessor == n)
							continue;
						if (n.Predecessor != null)
						{
							Cycle(current.Item1, n);
							return true;
						}
						stack.Push(new Tuple<Node<T>, Node<T>>(n, current.Item1));
					}
				}
			}
			return false;
		}

		private void Cycle(Node<T> last, Node<T> first)
		{
			Node<T> t = last;
			while (last != first)
			{
				System.Console.Out.WriteLine("{0} - {1}", last.Key, last.Predecessor.Key);
				last = last.Predecessor;
			}
			System.Console.Out.WriteLine("{0} - {1}", first.Key, t.Key);
		}
	}
}
