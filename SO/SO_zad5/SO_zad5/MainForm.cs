using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SO_zad5
{
	public partial class MainForm : Form
	{
		Block[] blocks;

		Results results;

		public MainForm(ref Results res)
		{
			results = res;
			InitializeComponent();
			blocks = new Block[Results.PROCESSOR_COUNT];
			int col = (int)Math.Ceiling(Math.Sqrt(Results.PROCESSOR_COUNT));
			tableLayoutPanel1.ColumnCount = col;
			tableLayoutPanel1.RowCount = col;
			tableLayoutPanel1.AutoSize = false;
			tableLayoutPanel1.ColumnStyles.Clear();
			tableLayoutPanel1.RowStyles.Clear();
			for (int i = 0; i < col; i++)
			{
				tableLayoutPanel1.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 100f / col));
				tableLayoutPanel1.RowStyles.Add(new ColumnStyle(SizeType.Percent, 100f / col));
			}
			for (int i = 0; i < Results.PROCESSOR_COUNT; i++)
			{
				blocks[i] = new Block(i, Results.processors[i]);
				tableLayoutPanel1.Controls.Add(blocks[i]);
			}
		}

		private void MainForm_Load(object sender, EventArgs e)
		{
			
		}

		private void MainForm_Paint(object sender, PaintEventArgs e)
		{
			//base.OnPaint(e);
			timeLabel.Text = "Time: " + results.time;
		}
	}
}
