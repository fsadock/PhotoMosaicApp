package app;

import fotomosaico.NoTilesException;
import java.io.IOException;
import java.io.FileNotFoundException;
import static fotomosaico.HandlesImages.*;

public class PhotoMosaicApp {

    public static void main(String[] args) throws IOException {

        try {
            String imgm = args[0];
            String ladrilho = args[1];
            String eps0 = args[2];
            String caminhoFinal = args[3];
            int eps = Integer.parseInt(eps0);
            montaImagem(imgm, ladrilho, eps, caminhoFinal);

        } catch (FileNotFoundException e) {
            System.err.println("\nErro: Diretorio final nao existe");

        } catch (IOException err) {
            System.err.println("\nErro: Nao foi possivel ler a imagem");

        } catch (NullPointerException er) {
            System.err.println("\nErro: Nao foi possivel ler os ladrilhos");

        } catch (ArrayIndexOutOfBoundsException error) {
            System.err.println("\nErro: Nao foi possivel ler todos os ladrilhos");

        } catch (AcabouLadrilhoException erro) {
            System.err.println("\nErro: Nao foi possivel completar a imagem (falta de ladrilhos)");

        } catch (NumberFormatException errr) {
            System.err.println("\nErro: Limiar informado invalido");
        }
    }
}
