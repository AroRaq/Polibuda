using System;
using System.Drawing;
using System.Windows.Forms;

namespace SO_zad5
{
	class Block : Button
	{
		private Processor processor;
		private int ID;

		Font font = new Font(DefaultFont, FontStyle.Regular);

		public Block(int id, Processor proc)
		{
			ID = id;
			processor = proc;
			this.Dock = DockStyle.Fill;
			this.FlatStyle = FlatStyle.Flat;
			this.Enabled = false;
			this.Paint += OnPaint;
		}

		protected void OnPaint(object sender, PaintEventArgs e)
		{
			this.BackColor = Color.FromArgb((int)(2.5 * processor.Usage), Math.Max(0, 255-(int)(2.5 * processor.Usage)), 0);
			Rectangle rect = ClientRectangle;
			rect.Inflate(-5, -5);
			using (StringFormat sf = new StringFormat() { Alignment = StringAlignment.Near, LineAlignment = StringAlignment.Near })
			{
				using (Brush brush = new SolidBrush(ForeColor))
				{
					e.Graphics.DrawString(processor.Usage.ToString() + "%", font, brush, rect, sf);
					
					sf.Alignment = StringAlignment.Far;
					e.Graphics.DrawString(processor.ProcessCount.ToString(), font, brush, rect, sf);
					/*
					sf.LineAlignment = StringAlignment.Far;
					pevent.Graphics.DrawString(TextWeek, font, brush, rect, sf);

					sf.Alignment = StringAlignment.Near;
					pevent.Graphics.DrawString(TextBottLeft, font, brush, rect, sf);
					*/
				}
			}
		}

	}
}
