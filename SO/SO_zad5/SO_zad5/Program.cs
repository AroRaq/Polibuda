using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
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
			formThread.IsBackground = false;
			formThread.Start(mainForm);
			//mainForm.Show();
			results.Run1();
		}

		private static void ApplicationRunProc(object state)
		{
			Application.Run(state as Form);
		}


		
	}
}
