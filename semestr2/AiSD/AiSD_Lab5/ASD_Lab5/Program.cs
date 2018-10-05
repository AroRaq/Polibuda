using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

namespace ASD_Lab5
{
	class Program
	{
		static void Main(string[] args)
		{
			BinarySearchTree<int> tree = DeSerializeObject<BinarySearchTree<int>>("tree.dat");
			//BinarySearchTree<int> tree = new BinarySearchTree<int> { 10, 5, 16, 2, 6, 15, 18, 1, 12, 17, 20, 21};
			tree.Display();
			
			string s = null;
			do
			{
				System.Console.Out.WriteLine();
				s = System.Console.ReadLine();
				string[] s1 = s.Split(' ');
				if (s1[0] == "add")
					tree.Add(int.Parse(s1[1]));
				if (s1[0] == "remove")
					tree.Remove(int.Parse(s1[1]));

				tree.Display();
			}
			while (s.Length != 0);
			//SerializeObject(tree, "tree.dat");
		}







		
		public static void SerializeObject<T>(T serializableObject, string fileName)
		{
			IFormatter formatter = new BinaryFormatter();
			FileStream s = new FileStream(fileName, FileMode.OpenOrCreate);
			formatter.Serialize(s, serializableObject);
			s.Close();
		}

		
		public static T DeSerializeObject<T>(string fileName)
		{
			IFormatter formatter = new BinaryFormatter();
			FileStream s = new FileStream(fileName, FileMode.OpenOrCreate);
			T obj = (T)formatter.Deserialize(s);
			s.Close();
			return obj;
		}
	}
}
