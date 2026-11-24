package repositorios;

import persistencia.FileManage;
import java.util.ArrayList;

public class ArchivoMensualRepository {
    private FileManage fileManager = new FileManage("Archivo_Mensual.txt");

    public void guardar(ArrayList<String> lineas) {
        fileManager.writeLinesToFile(lineas);
    }

    public ArrayList<String> consultar() {
        return fileManager.getDataOfFile();
    }
}
