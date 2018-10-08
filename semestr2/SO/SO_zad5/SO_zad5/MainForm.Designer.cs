namespace SO_zad5
{
	partial class MainForm
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
			this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
			this.timeLabel = new System.Windows.Forms.Label();
			this.labelAvgLoad1 = new System.Windows.Forms.Label();
			this.labelOdchylenie1 = new System.Windows.Forms.Label();
			this.labelRequests1 = new System.Windows.Forms.Label();
			this.labelMoves1 = new System.Windows.Forms.Label();
			this.labelAvgLoad2 = new System.Windows.Forms.Label();
			this.labelAvgLoad3 = new System.Windows.Forms.Label();
			this.labelOdchylenie2 = new System.Windows.Forms.Label();
			this.labelOdchylenie3 = new System.Windows.Forms.Label();
			this.labelRequests2 = new System.Windows.Forms.Label();
			this.labelRequests3 = new System.Windows.Forms.Label();
			this.labelMoves2 = new System.Windows.Forms.Label();
			this.labelMoves3 = new System.Windows.Forms.Label();
			this.tableLayoutPanel2.SuspendLayout();
			this.SuspendLayout();
			// 
			// tableLayoutPanel1
			// 
			this.tableLayoutPanel1.ColumnCount = 1;
			this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 108);
			this.tableLayoutPanel1.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.tableLayoutPanel1.Name = "tableLayoutPanel1";
			this.tableLayoutPanel1.RowCount = 1;
			this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this.tableLayoutPanel1.Size = new System.Drawing.Size(820, 479);
			this.tableLayoutPanel1.TabIndex = 0;
			// 
			// tableLayoutPanel2
			// 
			this.tableLayoutPanel2.ColumnCount = 5;
			this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 80F));
			this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
			this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
			this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
			this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
			this.tableLayoutPanel2.Controls.Add(this.timeLabel, 0, 1);
			this.tableLayoutPanel2.Controls.Add(this.labelAvgLoad1, 1, 0);
			this.tableLayoutPanel2.Controls.Add(this.labelOdchylenie1, 2, 0);
			this.tableLayoutPanel2.Controls.Add(this.labelRequests1, 3, 0);
			this.tableLayoutPanel2.Controls.Add(this.labelMoves1, 4, 0);
			this.tableLayoutPanel2.Controls.Add(this.labelAvgLoad2, 1, 1);
			this.tableLayoutPanel2.Controls.Add(this.labelAvgLoad3, 1, 2);
			this.tableLayoutPanel2.Controls.Add(this.labelOdchylenie2, 2, 1);
			this.tableLayoutPanel2.Controls.Add(this.labelOdchylenie3, 2, 2);
			this.tableLayoutPanel2.Controls.Add(this.labelRequests2, 3, 1);
			this.tableLayoutPanel2.Controls.Add(this.labelRequests3, 3, 2);
			this.tableLayoutPanel2.Controls.Add(this.labelMoves2, 4, 1);
			this.tableLayoutPanel2.Controls.Add(this.labelMoves3, 4, 2);
			this.tableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Top;
			this.tableLayoutPanel2.Location = new System.Drawing.Point(0, 0);
			this.tableLayoutPanel2.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.tableLayoutPanel2.Name = "tableLayoutPanel2";
			this.tableLayoutPanel2.RowCount = 3;
			this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 33.33333F));
			this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 33.33333F));
			this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 33.33333F));
			this.tableLayoutPanel2.Size = new System.Drawing.Size(820, 81);
			this.tableLayoutPanel2.TabIndex = 1;
			// 
			// timeLabel
			// 
			this.timeLabel.AutoSize = true;
			this.timeLabel.Dock = System.Windows.Forms.DockStyle.Fill;
			this.timeLabel.Location = new System.Drawing.Point(2, 26);
			this.timeLabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.timeLabel.Name = "timeLabel";
			this.timeLabel.Size = new System.Drawing.Size(76, 26);
			this.timeLabel.TabIndex = 0;
			this.timeLabel.Text = "Time:";
			this.timeLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.timeLabel.Paint += new System.Windows.Forms.PaintEventHandler(this.timeLabel_Paint);
			// 
			// labelAvgLoad1
			// 
			this.labelAvgLoad1.AutoSize = true;
			this.labelAvgLoad1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelAvgLoad1.Location = new System.Drawing.Point(83, 0);
			this.labelAvgLoad1.Name = "labelAvgLoad1";
			this.labelAvgLoad1.Size = new System.Drawing.Size(179, 26);
			this.labelAvgLoad1.TabIndex = 1;
			this.labelAvgLoad1.Text = "Obciążenie1:";
			this.labelAvgLoad1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelAvgLoad1.Paint += new System.Windows.Forms.PaintEventHandler(this.labelAvgLoad1_Paint);
			// 
			// labelOdchylenie1
			// 
			this.labelOdchylenie1.AutoSize = true;
			this.labelOdchylenie1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelOdchylenie1.Location = new System.Drawing.Point(268, 0);
			this.labelOdchylenie1.Name = "labelOdchylenie1";
			this.labelOdchylenie1.Size = new System.Drawing.Size(179, 26);
			this.labelOdchylenie1.TabIndex = 2;
			this.labelOdchylenie1.Text = "Odchylenie1:";
			this.labelOdchylenie1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelOdchylenie1.Paint += new System.Windows.Forms.PaintEventHandler(this.labelOdchylenie1_Paint);
			// 
			// labelRequests1
			// 
			this.labelRequests1.AutoSize = true;
			this.labelRequests1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelRequests1.Location = new System.Drawing.Point(453, 0);
			this.labelRequests1.Name = "labelRequests1";
			this.labelRequests1.Size = new System.Drawing.Size(179, 26);
			this.labelRequests1.TabIndex = 3;
			this.labelRequests1.Text = "Zapytania1: ";
			this.labelRequests1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelRequests1.Paint += new System.Windows.Forms.PaintEventHandler(this.labelRequests1_Paint);
			// 
			// labelMoves1
			// 
			this.labelMoves1.AutoSize = true;
			this.labelMoves1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelMoves1.Location = new System.Drawing.Point(638, 0);
			this.labelMoves1.Name = "labelMoves1";
			this.labelMoves1.Size = new System.Drawing.Size(179, 26);
			this.labelMoves1.TabIndex = 4;
			this.labelMoves1.Text = "Migracje1:";
			this.labelMoves1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelMoves1.Paint += new System.Windows.Forms.PaintEventHandler(this.labelMoves1_Paint);
			// 
			// labelAvgLoad2
			// 
			this.labelAvgLoad2.AutoSize = true;
			this.labelAvgLoad2.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelAvgLoad2.Location = new System.Drawing.Point(83, 26);
			this.labelAvgLoad2.Name = "labelAvgLoad2";
			this.labelAvgLoad2.Size = new System.Drawing.Size(179, 26);
			this.labelAvgLoad2.TabIndex = 5;
			this.labelAvgLoad2.Text = "Obciążenie2:";
			this.labelAvgLoad2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelAvgLoad2.Paint += new System.Windows.Forms.PaintEventHandler(this.labelAvgLoad2_Paint);
			// 
			// labelAvgLoad3
			// 
			this.labelAvgLoad3.AutoSize = true;
			this.labelAvgLoad3.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelAvgLoad3.Location = new System.Drawing.Point(83, 52);
			this.labelAvgLoad3.Name = "labelAvgLoad3";
			this.labelAvgLoad3.Size = new System.Drawing.Size(179, 29);
			this.labelAvgLoad3.TabIndex = 6;
			this.labelAvgLoad3.Text = "Obciążenie3:";
			this.labelAvgLoad3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelAvgLoad3.Paint += new System.Windows.Forms.PaintEventHandler(this.labelAvgLoad3_Paint);
			// 
			// labelOdchylenie2
			// 
			this.labelOdchylenie2.AutoSize = true;
			this.labelOdchylenie2.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelOdchylenie2.Location = new System.Drawing.Point(268, 26);
			this.labelOdchylenie2.Name = "labelOdchylenie2";
			this.labelOdchylenie2.Size = new System.Drawing.Size(179, 26);
			this.labelOdchylenie2.TabIndex = 7;
			this.labelOdchylenie2.Text = "Odchylenie2:";
			this.labelOdchylenie2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelOdchylenie2.Paint += new System.Windows.Forms.PaintEventHandler(this.labelOdchylenie2_Paint);
			// 
			// labelOdchylenie3
			// 
			this.labelOdchylenie3.AutoSize = true;
			this.labelOdchylenie3.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelOdchylenie3.Location = new System.Drawing.Point(268, 52);
			this.labelOdchylenie3.Name = "labelOdchylenie3";
			this.labelOdchylenie3.Size = new System.Drawing.Size(179, 29);
			this.labelOdchylenie3.TabIndex = 8;
			this.labelOdchylenie3.Text = "Odchylenie3:";
			this.labelOdchylenie3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelOdchylenie3.Paint += new System.Windows.Forms.PaintEventHandler(this.labelOdchylenie3_Paint);
			// 
			// labelRequests2
			// 
			this.labelRequests2.AutoSize = true;
			this.labelRequests2.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelRequests2.Location = new System.Drawing.Point(453, 26);
			this.labelRequests2.Name = "labelRequests2";
			this.labelRequests2.Size = new System.Drawing.Size(179, 26);
			this.labelRequests2.TabIndex = 9;
			this.labelRequests2.Text = "Zapytania2: ";
			this.labelRequests2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelRequests2.Paint += new System.Windows.Forms.PaintEventHandler(this.labelRequests2_Paint);
			// 
			// labelRequests3
			// 
			this.labelRequests3.AutoSize = true;
			this.labelRequests3.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelRequests3.Location = new System.Drawing.Point(453, 52);
			this.labelRequests3.Name = "labelRequests3";
			this.labelRequests3.Size = new System.Drawing.Size(179, 29);
			this.labelRequests3.TabIndex = 10;
			this.labelRequests3.Text = "Zapytania3: ";
			this.labelRequests3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelRequests3.Paint += new System.Windows.Forms.PaintEventHandler(this.labelRequests3_Paint);
			// 
			// labelMoves2
			// 
			this.labelMoves2.AutoSize = true;
			this.labelMoves2.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelMoves2.Location = new System.Drawing.Point(638, 26);
			this.labelMoves2.Name = "labelMoves2";
			this.labelMoves2.Size = new System.Drawing.Size(179, 26);
			this.labelMoves2.TabIndex = 11;
			this.labelMoves2.Text = "Migracje2:";
			this.labelMoves2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelMoves2.Paint += new System.Windows.Forms.PaintEventHandler(this.labelMoves2_Paint);
			// 
			// labelMoves3
			// 
			this.labelMoves3.AutoSize = true;
			this.labelMoves3.Dock = System.Windows.Forms.DockStyle.Fill;
			this.labelMoves3.Location = new System.Drawing.Point(638, 52);
			this.labelMoves3.Name = "labelMoves3";
			this.labelMoves3.Size = new System.Drawing.Size(179, 29);
			this.labelMoves3.TabIndex = 12;
			this.labelMoves3.Text = "Migracje3:";
			this.labelMoves3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.labelMoves3.Paint += new System.Windows.Forms.PaintEventHandler(this.labelMoves3_Paint);
			// 
			// MainForm
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(820, 587);
			this.Controls.Add(this.tableLayoutPanel2);
			this.Controls.Add(this.tableLayoutPanel1);
			this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.Name = "MainForm";
			this.Text = "MainForm";
			this.tableLayoutPanel2.ResumeLayout(false);
			this.tableLayoutPanel2.PerformLayout();
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
		private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
		private System.Windows.Forms.Label timeLabel;
		private System.Windows.Forms.Label labelAvgLoad1;
		private System.Windows.Forms.Label labelOdchylenie1;
		private System.Windows.Forms.Label labelRequests1;
		private System.Windows.Forms.Label labelMoves1;
		private System.Windows.Forms.Label labelAvgLoad2;
		private System.Windows.Forms.Label labelAvgLoad3;
		private System.Windows.Forms.Label labelOdchylenie2;
		private System.Windows.Forms.Label labelOdchylenie3;
		private System.Windows.Forms.Label labelRequests2;
		private System.Windows.Forms.Label labelRequests3;
		private System.Windows.Forms.Label labelMoves2;
		private System.Windows.Forms.Label labelMoves3;
	}
}