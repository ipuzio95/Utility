package pl.com.lessons.crypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class SymmetricEncryption 
{
	private String defaultDESKey = "lessons1";
	private Cipher ecipher;
	private Cipher dcipher;
    public SymmetricEncryption()
    {
        init();
    }

    public SymmetricEncryption(String key)
    {
        this.defaultDESKey = key;
        init();
    }
    private void init()//prywatna metoda dla klasy, ma pusta linie parametrow, nie zwraca wartosci
    {
        try
        {
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");//tworzenie obeiktu klasy metoda fabryk
            DESKeySpec ksp = new DESKeySpec(getKey().getBytes());

            SecretKey skey = kf.generateSecret(ksp);
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, skey);//inicjalizowanie zmiennej
            dcipher.init(Cipher.DECRYPT_MODE, skey);
        }
        catch (Exception e)
        {
            e.printStackTrace();//wypisanie info o bledzie, ale w przestrzen
        }
    }
    public String getKey()
    {
        return defaultDESKey;
    }
    public void setKey(String key)
    {
        this.defaultDESKey = key;
    }
    
    
    public String encrypt(String str) throws Exception
    {
        byte[] utf8 = str.getBytes("UTF8");//tekst na bajty
        byte[] enc = ecipher.doFinal(utf8);//finalne szyfrowanie
        Base64 base64 = new Base64();//metoda zamieniajaca tekst binanrny na postac dajaca sie wyswietlic
        return new String(base64.encode(enc));
    }

    public String decrypt(String str) throws Exception {
        Base64 base64 = new Base64();
        byte[] dec = base64.decode(str.getBytes());
        byte[] utf8 = dcipher.doFinal(dec);
        return new String(utf8, "UTF8");
    }
    
    
    
}
