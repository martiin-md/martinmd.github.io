package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GeneradorInformes {

    private static final String URL = "jdbc:sqlite:src/main/java/recursos/data.sqlite"; 

    private static final Logger LOGGER = Logger.getLogger(GeneradorInformes.class.getName());

    private Connection obtenerConexion() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(URL);
    }

    private void generarReporte(String reportResource, String reportCompilado, String reportPDF, String reportHTML, Map<String, Object> params) {
        Connection conexion = null;
        try {
            conexion = obtenerConexion();
            JasperReport reporte;

            if (reportCompilado != null && !reportCompilado.isEmpty()) {
                reporte = (JasperReport) JRLoader.loadObjectFromFile(reportCompilado);
            } else {
                reporte = JasperCompileManager.compileReport(reportResource);
            }

            JasperPrint informe = JasperFillManager.fillReport(reporte, params, conexion);
            JasperViewer.viewReport(informe, false);
            JasperExportManager.exportReportToPdfFile(informe, reportPDF);
            JasperExportManager.exportReportToHtmlFile(informe, reportHTML);

            System.out.println("Informe generado correctamente en PDF y HTML.");
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            LOGGER.log(Level.SEVERE, "Error al generar el reporte", ex);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                    System.out.println("Conexión cerrada correctamente.");
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Error al cerrar la conexión", ex);
                }
            }
        }
    }

    public void reporteEventos() {
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/eventos.jrxml",
                "src/main/java/informes/eventos.jasper",
                "src/main/java/informes/eventos.pdf",
                "eventos.html",
                params);
    }
    
    public void reporteGraficoVentasReservas(){
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/graficoVentas.jrxml",
                "src/main/java/informes/graficoVentas.jasper",
                "src/main/java/informes/graficoVentas.pdf",
                "graficoVentas.html",
                params);
        
    }
    
    public void reporteListaGenero(){
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/ListaEventoGeneros.jrxml",
                "src/main/java/informes/ListaEventoGeneros.jasper",
                "src/main/java/informes/ListaEventoGeneros.pdf",
                "listaGenero.html",
                params);
        
    }
    
    public void reporteArtistas(){
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/listaArtistas.jrxml",
                "src/main/java/informes/listaArtistas.jasper",
                "src/main/java/informes/listaArtistas.pdf",
                "listaArtistas.html",
                params);
        
    }
    
    public void reporteGraficoArtistasEventos(){
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/graficoCantArtistasEventos.jrxml",
                "src/main/java/informes/graficoCantArtistasEventos.jasper",
                "src/main/java/informes/graficoCantArtistasEventos.pdf",
                "graficoCantArtistasEventos.html",
                params);
        
    }
    
    public void reporteListaArtistasCategorias(){
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/artistasCategorias.jrxml",
                "src/main/java/informes/artistasCategorias.jasper",
                "src/main/java/informes/artistasCategorias.pdf",
                "artistasCategorias.html",
                params);
        
    }
    
    public void reporteListaReservas(){
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/listaReservas.jrxml",
                "src/main/java/informes/listaReservas.jasper",
                "src/main/java/informes/listaReservas.pdf",
                "listaReservas.html",
                params);
        
    }
    
    public void reporteGraficoReservasVentas(){
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/graficoVentasReservas.jrxml",
                "src/main/java/informes/graficoVentasReservas.jasper",
                "src/main/java/informes/graficoVentasReservas.pdf",
                "graficoVentasReservas.html",
                params);
        
    }
    
    public void reporteReservasPorFechas(){
        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Martin");
        params.put("imagePath", "src/cherry.jpg");
        generarReporte(
                "src/main/java/informes/ReservasPorFechas.jrxml",
                "src/main/java/informes/ReservasPorFechas.jasper",
                "src/main/java/informes/ReservasPorFechas.pdf",
                "ReservasPorFechas.html",
                params);
        
    }
      
}
