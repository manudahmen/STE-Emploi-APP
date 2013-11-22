package steemploi.statistiques;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.encoders.ServletEncoderHelper;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.LineChartProperties;
import org.jCharts.properties.PointChartProperties;
import org.jCharts.properties.PropertyException;
import org.jCharts.properties.util.ChartFont;
import org.jCharts.test.TestDataGenerator;
import org.jCharts.types.ChartType;

import com.myapp.struts.CategoriesTaches;

import steemploi.persistance.*;
import steemploi.service.*;

/**
 * Servlet implementation class EtudiantTaches
 */
public class EtudiantTaches extends HttpServlet {
	private AxisChart axisChart = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EtudiantTaches() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Calendar date = Calendar.getInstance();
		if(request.getParameter("date")!=null && request.getParameter("date")!="")
		{
			try {
				date.setTime(DateFormat.getDateInstance(DateFormat.SHORT,
				        Locale.FRANCE).parse(request.getParameter("date")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int id = Integer.parseInt(request.getParameter("id"));
		TableStat ts = new TableStat();
		TableSessionsFormations tsf = new TableSessionsFormations();

		TableCodeCategorieTache tcodes = new TableCodeCategorieTache();
		List<CategoriesTache> codes = null;
		try {
			codes =  tcodes.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<SessionsFormations> sfs = null;
		try {
			sfs = tsf.findAll(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Set<Etudiant> etudiants = null;
		for(SessionsFormations sf : sfs)
		{
			HashMap<Etudiant, ArrayList<TacheStatItem>> map = null;
			try {
				map = ts.getStats(sf, date);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			etudiants = map.keySet();
			for(Etudiant e : etudiants)
			{
				if(id==e.getId())
				{		
					ArrayList<TacheStatItem> items = map.get(e);
					graph(items, codes);
					try {
						writePicture(response);
					} catch (ChartDataException e1) {
						e1.printStackTrace();
					} catch (PropertyException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
	}


	private void graph(ArrayList<TacheStatItem> items,
			List<CategoriesTache> codes) {
		LegendProperties legendProperties = new LegendProperties();
		ChartProperties chartProperties = new ChartProperties();
		AxisProperties axisProperties = new AxisProperties( false );

		ChartFont axisScaleFont = new ChartFont( new Font( "Georgia Negreta cursiva", Font.PLAIN, 13 ), Color.black );
		axisProperties.getXAxisProperties().setScaleChartFont( axisScaleFont );
		axisProperties.getYAxisProperties().setScaleChartFont( axisScaleFont );

		ChartFont axisTitleFont = new ChartFont( new Font( "Arial Narrow", Font.PLAIN, 14 ), Color.black );
		axisProperties.getXAxisProperties().setTitleChartFont( axisTitleFont );
		axisProperties.getYAxisProperties().setTitleChartFont( axisTitleFont );

		Stroke[] strokes= { LineChartProperties.DEFAULT_LINE_STROKE, LineChartProperties.DEFAULT_LINE_STROKE, LineChartProperties.DEFAULT_LINE_STROKE };
		Shape[] shapes= { PointChartProperties.SHAPE_TRIANGLE,PointChartProperties.SHAPE_DIAMOND, PointChartProperties.SHAPE_CIRCLE };
		LineChartProperties lineChartProperties = new LineChartProperties(strokes,shapes);
		String[] xAxisLabels= new String [codes.size()]; 
		int i=0;
		for(CategoriesTache code : codes)
		{
			 xAxisLabels [i++] = codes.get(i).getTitle();
		}
		String xAxisTitle= "Taches";
		String yAxisTitle= "Total";
		String title= "Statistique de tâches pour l'étudiant";
		DataSeries dataSeries = new DataSeries( xAxisLabels, xAxisTitle, yAxisTitle,title );
		
				
		//From AxisChartServlet.java:createAxisChartDataSet
		double[][] data=new double[codes.size()][1];
		i=0;
		for(CategoriesTache code : codes)
		{
			for(TacheStatItem ct : items)
			{
				if(ct.getCode().equals(code))
				{
					data[i++][0]=ct.getCount();
				}
			}
		}
		String[] legendLabels= xAxisLabels;
		Paint[] paints= TestDataGenerator.getRandomPaints( 3 );
		AxisChartDataSet acds = null;
		try {
			acds = new AxisChartDataSet(data, legendLabels, paints,ChartType.LINE, lineChartProperties );
		} catch (ChartDataException e) {
			e.printStackTrace();
		}
		dataSeries.addIAxisPlotDataSet(acds);
		axisChart = new AxisChart(dataSeries, chartProperties, axisProperties,legendProperties, 550, 360);
	}
	private void writePicture(HttpServletResponse response) throws ChartDataException, PropertyException, IOException
	{
		ServletEncoderHelper.encodeJPEG13(axisChart, 1.0f, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
