using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ASD_Lab5
{
	[Serializable]
	public class BinarySearchTree<T>:IEnumerable<T>, ISerializable where T:IComparable
	{
		[Serializable]
		internal class TreeNode<E>:ISerializable where E:IComparable
		{
			internal E Key;
			internal TreeNode<E> LeftChild = null;
			internal TreeNode<E> RightChild = null;
			internal TreeNode<E> Parent = null;

			internal TreeNode()
			{

			}

			protected TreeNode(SerializationInfo info, StreamingContext context)
			{
				Key = (E)info.GetValue("Key", typeof(E));
				Parent = (TreeNode<E>)info.GetValue("Parent", typeof(TreeNode<E>));
				LeftChild = (TreeNode<E>)info.GetValue("LeftChild", typeof(TreeNode<E>));
				RightChild = (TreeNode<E>)info.GetValue("RightChild", typeof(TreeNode<E>));
			}

			internal TreeNode(E key, TreeNode<E> parent = null)
			{
				Key = key;
				Parent = parent;
			}

			internal void Add(E val)
			{
				if (val.CompareTo(Key) <= 0)
				{
					if (LeftChild == null)
						LeftChild = new TreeNode<E>(val, this);
					else
						LeftChild.Add(val);
				}
				else
				{
					if (RightChild == null)
						RightChild = new TreeNode<E>(val, this);
					else
						RightChild.Add(val);
				}
			}
			
			internal TreeNode<E> FindMax()
			{
				if (RightChild == null)
					return this;
				return RightChild.FindMax();
			}

			internal TreeNode<E> FindMin()
			{
				if (LeftChild == null)
					return this;
				return LeftChild.FindMin();
			}

			internal bool IsLeaf()
			{
				return LeftChild == null && RightChild == null;
			}

			internal TreeNode<E> FindKey(E key)
			{
				if (Key.Equals(key))
					return this;
				if (key.CompareTo(Key) < 0)
					if (LeftChild != null)
						return LeftChild.FindKey(key);
				if (key.CompareTo(Key) > 0)
					if (RightChild != null)
						return RightChild.FindKey(key);
				return null;
			}

			internal R Preorder<R>(IExecutor<E, R> exec)
			{
				exec.Execute(Key);
				if (LeftChild != null)
					LeftChild.Preorder<R>(exec);
				if (RightChild != null)
					RightChild.Preorder<R>(exec);
				return exec.GetResult();
			}

			internal R Postorder<R>(IExecutor<E, R> exec)
			{
				if (LeftChild != null)
					LeftChild.Postorder(exec);
				if (RightChild != null)
					RightChild.Postorder(exec);
				exec.Execute(Key);
				return exec.GetResult();
			}

			internal R Inorder<R>(IExecutor<E, R> exec)
			{
				if (LeftChild != null)
					LeftChild.Inorder(exec);
				exec.Execute(Key);
				if (RightChild != null)
					RightChild.Inorder(exec);
				return exec.GetResult();
			}

			internal R BFS<R>(IExecutor<E, R> exec)
			{
				Queue<TreeNode<E>> que = new Queue<TreeNode<E>>();
				que.Enqueue(this);
				while (que.Any())
				{
					TreeNode<E> temp = que.Dequeue();
					exec.Execute(temp.Key);
					if (temp.LeftChild != null)
						que.Enqueue(temp.LeftChild);
					if (temp.RightChild != null)
						que.Enqueue(temp.RightChild);
				}
				return exec.GetResult();
			} 

			internal TreeNode<E> Successor()
			{
				if (RightChild != null)
					return RightChild.FindMin();
				TreeNode<E> par = Parent;
				TreeNode<E> down = this;
				while (par != null)
				{
					if (par.LeftChild == down)
						return par;
					down = par;
					par = par.Parent;
				}
				return null;
			}

			public TreeNode<E> Remove(E key)
			{
				if (key.CompareTo(Key) < 0)
				{
					if (LeftChild == null)
						return this;
					LeftChild = LeftChild.Remove(key);
				}
				else if (key.CompareTo(Key) > 0)
				{
					if (RightChild == null)
						return this;
					RightChild = RightChild.Remove(key);
				}
				if (key.Equals(Key))
				{
					if (IsLeaf())
					{
						return null;
					}
					if (LeftChild == null && RightChild != null)
					{
						return RightChild;
					}
					if (RightChild == null && LeftChild != null)
					{
						return LeftChild;
					}
					TreeNode<E> succ = Successor();
					if (succ.Parent.LeftChild == succ)
					{
						succ.Parent.LeftChild = succ.RightChild;
						if (succ.RightChild != null)
							succ.RightChild.Parent = succ.Parent;
					}
					if (succ.Parent.RightChild == succ)
					{
						succ.Parent.RightChild = succ.RightChild;
						if (succ.RightChild != null)
							succ.RightChild.Parent = succ.Parent;
					}
					if (LeftChild != null)
						LeftChild.Parent = succ;
					if (RightChild != null)
						RightChild.Parent = succ;
					succ.LeftChild = LeftChild;
					succ.RightChild = RightChild;
					succ.Parent = Parent;
					return succ;
				}
				return this;
			}

			public void GetObjectData(SerializationInfo info, StreamingContext context)
			{
				info.AddValue("Parent", Parent);
				info.AddValue("Key", Key);
				info.AddValue("LeftChild", LeftChild);
				info.AddValue("RightChild", RightChild);
			}
		}

		protected BinarySearchTree(SerializationInfo info, StreamingContext context)
		{
			if (info == null)
				throw new ArgumentNullException("info");

			Root = new TreeNode<T>();
			Root = (TreeNode<T>)info.GetValue("Root", typeof(TreeNode<T>));
		}

		public BinarySearchTree()
		{

		}
		
		TreeNode<T> Root = null;

		public void Add(T item)
		{
			if (Root == null)
				Root = new TreeNode<T>(item);
			else
				Root.Add(item);
		}

		public bool Contains(T item)
		{
			return Root.FindKey(item) != null;
		}

		public void Remove(T item)
		{
			Root = Root.Remove(item);
		}

		public void Display()
		{
			
			Console.Out.Write("Preorder:  ");
			Root.Preorder(new DisplayExec<T, String>());
			Console.Out.WriteLine();

			Console.Out.Write("Postorder: ");
			Root.Postorder(new DisplayExec<T, String>());
			Console.Out.WriteLine();

			Console.Out.Write("Inorder:   ");
			Root.Inorder(new DisplayExec<T, String>());
			Console.Out.WriteLine();

			Console.Out.Write("BFS:       ");
			Root.BFS(new DisplayExec<T, String>());
			Console.Out.WriteLine();

		}

		public IEnumerator<T> GetEnumerator()
		{
			Queue<TreeNode<T>> que = new Queue<TreeNode<T>>();
			que.Enqueue(Root);
			while (que.Any())
			{
				TreeNode<T> temp = que.Dequeue();
				if (temp.LeftChild != null)
					que.Enqueue(temp.LeftChild);
				if (temp.RightChild != null)
					que.Enqueue(temp.RightChild);
				yield return temp.Key;
			}
		}

		IEnumerator IEnumerable.GetEnumerator()
		{
			throw new NotImplementedException();
		}

		public void GetObjectData(SerializationInfo info, StreamingContext context)
		{
			info.AddValue("Root", Root);
		}
	}
}
