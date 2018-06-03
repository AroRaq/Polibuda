using System.Collections.Generic;
using System.Threading;
using System.Windows.Forms;

namespace SO_zad5
{
	class Program
	{
		static void Main(string[] args)
		{
			Application.EnableVisualStyles();
			Results results = new Results();
			MainForm mainForm = new MainForm(ref results);
			Thread formThread = new Thread(ApplicationRunProc);
			Thread formThread1 = new Thread(ApplicationRunProc1);
			formThread.IsBackground = false;
			formThread.Start(mainForm);
			formThread1.Start(mainForm);
			results.Run1();
			results.Run2();
			results.Run3();
		}

		private static void ApplicationRunProc(object state)
		{
			Application.Run(state as Form);
			System.Console.Out.WriteLine("EHR");
		}

		private static void ApplicationRunProc1(object state)
		{
			Form f = state as Form;
			while (true)
			{
				f.Refresh();
				var list = GetAllControls(f, new List<Control>());
				//foreach (Control c in list)
					//c.Refresh();
			}
		}

		private static List<Control> GetAllControls(Control container, List<Control> list)
		{
			foreach (Control c in container.Controls)
			{
				if (c is Label)
					list.Add(c);
				if (c.Controls.Count > 0)
					list.AddRange(GetAllControls(c, list));
			}
			return list;
		}



	}
}
