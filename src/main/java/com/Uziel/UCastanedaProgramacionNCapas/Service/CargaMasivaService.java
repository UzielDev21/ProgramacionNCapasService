package com.Uziel.UCastanedaProgramacionNCapas.Service;

import io.jsonwebtoken.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class CargaMasivaService {
    
    private static final String LOG_PATH = "src/main/resources/Log_CargaMasiva.txt";
    
    public void writeLog(String nombreArchivo, String token, String status, String descripcion) throws java.io.IOException{
        FileWriter fileWriter = null;
        try {
            File file = new File(LOG_PATH);
            String fechaHora = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            String tokenSafe = (token == null ? "null" : token);
            
            String linea = nombreArchivo + " | " +
                    tokenSafe + " | " + 
                    status + " | " + 
                    fechaHora + " | " +
                    descripcion;
            
            fileWriter = new FileWriter(file, true);
            fileWriter.write(linea + System.lineSeparator());
        } catch (Exception ex) {
            System.out.println("Error al escribir en Log_CargaMasiva" + ex.getLocalizedMessage());
        }finally{
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}
