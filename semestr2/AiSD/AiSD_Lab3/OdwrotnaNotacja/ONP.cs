using System;
using System.Collections.Generic;
using System.Linq;

namespace OdwrotnaNotacja
{
	class ONP
	{
		private static string OPERATOR_SYMBOLS = "+-*/^";

		private static int Priority(char op)
		{
			if (op == '(' || op == ')') return 0;
			if (op == '+' || op == '-') return 1;
			if (op == '*' || op == '/')	return 2;
			return 3;
		}

		public static string ToONP(string s)
		{
			Queue<string> que = new Queue<string>();
			string temp = "";
			for (int i = 0; i < s.Length; i++)
			{
				if (s[i].Equals(' '))
					continue;
				if ("()+-*/^=".Contains(s[i]))
				{
					if (temp.Length != 0)
					{
						que.Enqueue(temp);
						temp = "";
					}
					que.Enqueue(s[i].ToString());
				}
				else
					temp += s[i];
			}
			if (temp.Length != 0)
				que.Enqueue(temp);
			Stack<char> op = new Stack<char>();
			return ToONP(ref que, ref op);
		}

		private static string ToONP(ref Queue<string> que, ref Stack<char> operators)
		{
			if (!que.Any() || que.Peek().Equals("="))
			{
				string s = "";
				while (operators.Any())
					s += operators.Pop() + " ";
				return s;
			}
			else if (que.Peek().Equals(" "))
			{
				que.Dequeue();
				return ToONP(ref que, ref operators);
			}
			else if (que.Peek().Equals("("))
			{
				operators.Push(que.Dequeue()[0]);
				return ToONP(ref que, ref operators);
			}
			else if (que.Peek().Equals(")"))
			{
				string s = "";
				while (operators.Peek() != '(')
				{
					s += operators.Pop() + " ";
				}
				operators.Pop();
				que.Dequeue();
				return s + ToONP(ref que, ref operators);
			}
			else if (OPERATOR_SYMBOLS.Contains(que.Peek()))
			{
				char op = que.Dequeue().ToCharArray()[0];
				string s = "";
				while (operators.Any())
				{
					if (Priority(op) == 3 || Priority(op) > Priority(operators.Peek()))
					{
						break;
					}
					s += operators.Pop() + " ";
				}
				operators.Push(op);
				return s + ToONP(ref que, ref operators);
			}
			else
			{
				return que.Dequeue() + " " + ToONP(ref que, ref operators);
			}
		}

		public static double Calculate(String s)
		{
			Stack<double> stack = new Stack<double>();
			string[] op = s.Split(' ');
			foreach (string c in op)
			{
				if (c.Equals(""))
					continue;
				if (double.TryParse(c, out double d))
				{
					stack.Push(d);
				}
				else if (OPERATOR_SYMBOLS.Contains(c))
				{
					double d1 = stack.Pop();
					double d2 = stack.Pop();
					if (c == "+") stack.Push(d1 + d2);
					if (c == "-") stack.Push(d2 - d1);
					if (c == "*") stack.Push(d1 * d2);
					if (c == "/") stack.Push(d2 / d1);
					if (c == "^") stack.Push(Math.Pow(d2, d1));
				}
			}
			return stack.Pop();
		}

	}
}
