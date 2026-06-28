package org.example.carwashfx.report;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import org.example.carwashfx.controllers.exceptions.ExceptionWash;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

public class ServiceOrderReport {

    private static final String TEMPLATE_PATH = "/org/example/carwashfx/reports/service-order-report.jrxml";

    public void showAllServiceOrdersReport(Connection connection) {
        try (InputStream template = getClass().getResourceAsStream(TEMPLATE_PATH)) {
            if (template == null) {
                throw new ExceptionWash("Modelo Jasper do relatório não encontrado: " + TEMPLATE_PATH);
            }

            JasperReport report = JasperCompileManager.compileReport(template);
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), connection);
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            throw new ExceptionWash("Erro ao gerar relatório Jasper: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ExceptionWash("Erro ao preparar relatório: " + e.getMessage(), e);
        }
    }
}
