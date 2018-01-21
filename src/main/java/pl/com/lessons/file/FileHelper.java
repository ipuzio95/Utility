package pl.com.lessons.file;
zuza tu była 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class FileHelper {
//	private static final Pattern pat = Pattern.compile("_(\\d*)\\.");
    private static final String ENCODING = "UTF-8";
//    private static Logger logger = CustomLogger.getLogger(FileHelper.class.getCanonicalName());
    
	public static void writeToFile(String value, String filePath, boolean append) throws IOException
    {
        BufferedWriter out = null;
        try
        {
            out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(new File(filePath), append), ENCODING));
            out.write(value);
        } catch (IOException e)
        {
            throw e;
        } finally
        {
            out.flush();
            out.close();
        }
    }
	public static String[] listFiles(String dirName)
    {
        File dir = new File(dirName);

        String[] files = dir.list();
        return files;
    }
	public static void listFilesRecursively(String dirName, List<String> files)
    {
        File dir = new File(dirName);
        String[] lFiles = dir.list();

        for (String file : lFiles)
        {
            File f = new File(dir + "/" + file);//dir może być bo z klasy brana jest nazwa pliku która jest String 
            files.add(f.getAbsolutePath());
            if (f.isDirectory())
            {
                listFilesRecursively(f.getAbsolutePath(), files);
            }
        }
    }

}
