using System;
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

		private void timeLabel_Paint(object sender, PaintEventArgs e) => timeLabel.Text = "Time: \n" + results.time;

		private void labelAvgLoad1_Paint(object sender, PaintEventArgs e) => labelAvgLoad1.Text = "Obciążenie1: " + results.AvgLoad[0];
		private void labelOdchylenie1_Paint(object sender, PaintEventArgs e) => labelOdchylenie1.Text = "Odchylenie1: " + results.AvgDeviation[0];
		private void labelRequests1_Paint(object sender, PaintEventArgs e) => labelRequests1.Text = "Zapytania1: " + Results.Requests[0];
		private void labelMoves1_Paint(object sender, PaintEventArgs e) => labelMoves1.Text = "Migracje1: " + Results.Moves[0];

		private void labelAvgLoad2_Paint(object sender, PaintEventArgs e) => labelAvgLoad2.Text = "Obciążenie2: " + results.AvgLoad[1];
		private void labelOdchylenie2_Paint(object sender, PaintEventArgs e) => labelOdchylenie2.Text = "Odchylenie2: " + results.AvgDeviation[1];
		private void labelRequests2_Paint(object sender, PaintEventArgs e) => labelRequests2.Text = "Zapytania2: " + Results.Requests[1];
		private void labelMoves2_Paint(object sender, PaintEventArgs e) => labelMoves2.Text = "Migracje2: " + Results.Moves[1];

		private void labelAvgLoad3_Paint(object sender, PaintEventArgs e) => labelAvgLoad3.Text = "Obciążenie3: " + results.AvgLoad[2];
		private void labelOdchylenie3_Paint(object sender, PaintEventArgs e) => labelOdchylenie3.Text = "Odchylenie3: " + results.AvgDeviation[2];
		private void labelRequests3_Paint(object sender, PaintEventArgs e) => labelRequests3.Text = "Zapytania3: " + Results.Requests[2];
		private void labelMoves3_Paint(object sender, PaintEventArgs e) => labelMoves3.Text = "Migracje3: " + Results.Moves[2];
		
	}
}
