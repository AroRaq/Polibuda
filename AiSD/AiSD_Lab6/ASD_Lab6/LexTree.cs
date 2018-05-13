using System;
using System.Collections;
using System.Collections.Generic;

namespace ASD_Lab6
{
	class LexTree : ICollection<string>
	{
		internal class Node
		{
			private Node parent;
			internal List<Node> children;
			private char key;
			internal int wordCount;

			public bool IsEnd;
			public char Key
			{
				get { return key; }
			}
			public int WordCount
			{
				get { return wordCount; }
			}
			public string Prefix
			{
				get
				{
					string s = "";
					Node current = this;
					while (current.Key != (char)0)
					{
						s = current.Key + s;
						current = current.parent;
					}
					return s;
				}
			}
			public int ChildrenCount
			{
				get
				{
					return children.Count;
				}
			}

			internal Node(char _key, Node _parent = null)
			{
				children = new List<Node>();
				parent = _parent;
				key = _key;
				IsEnd = false;
				wordCount = 0;
			}

			internal void Put(Node n)
			{
				children.Add(n);
			}

			internal bool Remove(char _key)
			{
				foreach (Node n in children)
					if (n.Key == _key)
					{
						children.Remove(n);
						return true;
					}
				return false;
			}

			internal Node Get(char c)
			{
				foreach (Node n in children)
					if (n.Key == c)
						return n;
				return null;
			}

			internal bool ContainsKey(char c)
			{
				return Get(c) != null;
			}

			internal bool ContainsWord(string s)
			{
				if (s.Length == 0)
				{
					if (IsEnd)
						return true;
					else
						return false;
				}
				Node n = Get(s[0]);
				if (n == null)
					return false;
				return n.ContainsWord(s.Substring(1));
			}

			internal List<string> ReturnAll()
			{
				List<string> list = new List<string>();

				foreach (Node n in children)
					list.AddRange(n.ReturnAll());

				if (Key != (char)0)
					for (int i = 0; i < list.Count; i++)
						list[i] = Key + list[i];

				if (IsEnd)
					list.Add(Key.ToString());

				return list;
			}

			private bool IsLeaf()
			{
				return children.Count == 0;
			}

			internal void Add(string s)
			{
				Node current = this;
				wordCount++;
				foreach (char c in s)
				{
					if (!current.ContainsKey(c))
						current.Put(new Node(c, current));
					current = current.Get(c);
					current.wordCount++;
				}
				current.IsEnd = true;
			}

			public List<Node> NDown(int n)
			{
				List<Node> list = new List<Node>();
				if (n == 1)
				{
					list.Add(this);
					return list;
				}
				foreach (Node no in children)
				{
					if (no != null)
					{
						list.AddRange(no.NDown(n - 1));
					}
				}
				return list;
			}

			public int NodeCount()
			{
				if (IsLeaf())
					return 1;
				int t = 1;
				foreach (Node n in children)
					t += n.NodeCount();
				return t;
			}
		}




		Node Root;
		public int Count
		{
			get
			{
				return Root.WordCount;
			}
		}

		bool ICollection<string>.IsReadOnly => throw new NotImplementedException();

		public LexTree()
		{
			Root = new Node((char)0);
		}
		
		public void Add(string word)
		{
			if (!Contains(word))
				Root.Add(word);
		}
		
		public void Clear()
		{
			Root = new Node((char)0);
		}

		public bool Contains(string item)
		{
			return Root.ContainsWord(item);
		}

		public List<string> ReturnAllPrefix(string prefix)
		{
			Node n = FindPrefix(prefix);
			if (n == null)
				return null;

			List<string> list = n.ReturnAll();
			if (prefix.Length == 0)
				return list;
			prefix = prefix.Substring(0, prefix.Length - 1);
			for (int i = 0; i < list.Count; i++)
				list[i] = prefix + list[i];
			return list;
		}

		internal Node FindPrefix(string prefix)
		{
			Node current = Root;
			foreach (char c in prefix)
			{
				if (!current.ContainsKey(c))
					return null;
				current = current.Get(c);
			}
			return current;
		}

		public string MostOften(int length)
		{
			List<Node> list = Root.NDown(length+1);
			if (list.Count == 0)
				return "Brak";
			int max = 0;
			int idx = 0;
			for (int i = 0; i < list.Count; i++)
			{
				if (list[i].WordCount > max)
				{
					max = list[i].WordCount;
					idx = i;
				}
			}
			return list[idx].Prefix;

		}

		
		public string MostOften2(int length)
		{
			if (length == 0)
				length = 1;
			List<Node> list = Root.NDown(length+1);
			if (list.Count == 0)
				return "Brak";

			List<int> max = new List<int>();
			max.Add(0);
			for (int i = 1; i < list.Count; i++)
			{
				if (list[i].WordCount == list[max[0]].WordCount)
					max.Add(i);
				if (list[i].WordCount > list[max[0]].WordCount)
				{
					max.Clear();
					max.Add(i);
				}
			}
			int maxCount = list[max[0]].WordCount;
			string maxString = "";
			int maxStringLength = 0;
			Stack<Node> stack = new Stack<Node>();
			foreach (int i in max)
				stack.Push(list[i]);

			while (stack.Count != 0)
			{
				Node curr = stack.Pop();
				if (curr.Prefix.Length == maxStringLength)
				{
					maxString += " " + curr.Prefix;
				}
				if (curr.Prefix.Length > maxStringLength)
				{
					maxString = curr.Prefix;
					maxStringLength = maxString.Length;
				}
				foreach (Node n in curr.children)
				{
					if (n.WordCount == maxCount)
					{
						stack.Push(n);
					}
				}
			}
			return maxString;

		}


		public void CopyTo(string[] array, int arrayIndex)
		{
			List<string> list = ReturnAllPrefix("");
			array = list.ToArray();
		}

		public IEnumerator<string> GetEnumerator()
		{
			List<string> list = ReturnAllPrefix("");
			foreach (string s in list)
				yield return s;
		}

		public bool Remove(string item)
		{
			if (!Contains(item))
				return false;
			Node current = Root;
			current.wordCount--;
			foreach (char c in item)
			{
				current = current.Get(c);
				current.wordCount--;
			}
			current.IsEnd = false;
			return true;
		}

		public int NodeCount()
		{
			return Root.NodeCount();
		}

		public int PrefixCount(string prefix)
		{
			return FindPrefix(prefix).WordCount;
		}

		IEnumerator IEnumerable.GetEnumerator()
		{
			throw new NotImplementedException();
		}
	}
}
