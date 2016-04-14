package fotomosaico;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ManipulaImagens {

    private static BufferedImage atual;
    private static int utilizado = 0;
    private static int mediaPixel;
    private static int mediaLadrilho;
    private static int widthLad;
    private static int heightLad;
    private final static int black = -16777216;          //VALOR PADRAO DO PIXEL DA IMAGEM CRIADA

    public static File[] lerLadrilho(String path) throws IOException {
        File[] arrayLad;
        File lad = new File(path);
        if (!lad.isDirectory()) {
            throw new NullPointerException();
        }
        arrayLad = lad.listFiles();
        return arrayLad;
    }

    public static int getMediaPixel(int[] array) {

        int soma = 0, i;
        for (i = 0; i < array.length; i++) {            //CALCULA
            int red = (array[i] >> 16) & 0xFF;         //A
            int green = (array[i] >> 8) & 0xFF;       //MEDIA
            int blue = (array[i]) & 0xFF;            //DE
            int mediaP = (red + green + blue) / 3;  //UM
            soma += mediaP;                        //ARRAY
        }                                         //DE
        mediaLadrilho = soma / i;                //INTEIROS
        return mediaLadrilho;                   //(USADO PARA O LADRILHO)
    }

    public static int getMediaPixel(BufferedImage img, int x, int y) {

        int red = (img.getRGB(x, y) >> 16) & 0xFF;      //CALCULA
        int green = (img.getRGB(x, y) >> 8) & 0xFF;    //MEDIA
        int blue = (img.getRGB(x, y)) & 0xFF;         //DO
        mediaPixel = (red + green + blue) / 3;       //PIXEL (x,y)
        return mediaPixel;                          //DA IMAGEM (USADO PARA IMAGEM BASE)
    }

    public static void montaImagem(String imgm, String ladrilho,
            int eps, String caminhoFinal) throws IOException, AcabouLadrilhoException {

        //INICIALIZA VARIAVEIS
        int[] array0;
        int x, j, w0, h0;

        BufferedImage img = ImageIO.read(new File(imgm));
        File[] arrayLad = lerLadrilho(ladrilho);           //PARA PEGAR A LARGURA E ALTURA
        BufferedImage lad = ImageIO.read(arrayLad[0]);    //ASSUMINDO TODOS OS                                         
        BufferedImage novaImg = criaImagem(img, lad);    // LADRILHOS SAO IGUAIS
        w0 = img.getWidth();
        h0 = img.getHeight();

        //COMEÇA PROCESSAMENTO

        superloop:
        for (File arrayLad1 : arrayLad) {

            atual = ImageIO.read(arrayLad1);

            if (atual != null) {                   //VERIFICA SE O PROXIMO LADRILHO EXISTE NA PASTA
                widthLad = atual.getWidth();
                heightLad = atual.getHeight();
                array0 = atual.getRGB(0, 0, widthLad, heightLad, null, 0, widthLad);   //LADRILHO EM ARRAY DE PIXELS
                getMediaPixel(array0);

                outerloop:
                for (x = 0; x < w0; x++) {       //x E j MENORES QUE DIMENSOES
                    for (j = 0; j < h0; j++) {  //DA IMAGEM BASE

                        getMediaPixel(img, x, j);

                        if (Math.abs(Math.abs(mediaLadrilho) - Math.abs(mediaPixel)) <= eps //VALOR ABSOLUTO DA DIFERENÇA
                                && novaImg.getRGB(x * widthLad, j * heightLad) == black) { //OU SEJA, SÓ MUDA SE O PIXEL(x,j)
                            //NA IMAGEM FINAL FOR PRETO (PARA EVITAR ESCREVER EM CIMA DE OUTRO LADRILHO)

                            novaImg.setRGB(x * widthLad, j * heightLad, widthLad, heightLad,
                                    array0, 0, widthLad);

                            //if (utilizado == 9) {    //CONTROLA 
                            //    utilizado = 0;      //A
                            //    break outerloop;   //REPETIÇÃO
                            //}
                            //utilizado++;
                        }
                    }
                }
            }
        }
        File caminhoFin = new File(caminhoFinal);

        verificaDiretorioFinal(caminhoFinal);

        ImageIO.write(novaImg, "jpg", caminhoFin); //ESCREVE A IMAGEM NO CAMINHO
    }                                             //ESPECIFICADO

    public static BufferedImage criaImagem(BufferedImage img, BufferedImage lad) {

        //CRIA IMAGEM FINAL COM DIMENSOES PROPORCIONAIS A IMAGEM BASE E LADRILHO
        BufferedImage nImg = new BufferedImage(img.getWidth() * lad.getWidth(),
                img.getHeight() * lad.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        return nImg;
    }

    public static void verificaDiretorioFinal(String caminhoFinal) throws IOException {
        
        String diretorio = "";
        String delimitador = "\\\\";
        String[] vetorString = caminhoFinal.split(delimitador);    //SEPARA
        for (int i = 0; i < vetorString.length - 1; i++) {        //O
            if (diretorio.equals("")) {                          //NOME DA
                diretorio += "" + vetorString[i];               //IMAGEM
            } else {                                           //DO
                diretorio += "\\" + vetorString[i];           //DIRETORIO
            }                                                //ESCOLHIDO
        }

        File diretorioF = new File(diretorio);    //VERIFICA SE O 
        if (!diretorioF.isDirectory()) {         //DIRETORIO DE
            throw new FileNotFoundException();  //ESCRITA EXISTE
        }
    }

}
