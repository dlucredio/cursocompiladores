# Construção de Compiladores - Daniel Lucrédio, Helena Caseli, Mário César San Felice e Murilo Naldi
## Customizando as mensagens de erro do ANTLR

Para os trabalhos, vocês precisarão salvar a saída em um arquivo especificado pelo usuário (conforme especificações dos trabalhos). Existem várias formas de se fazer isso. Em Java, pode ser assim:

```java
package <seu pacote aqui>;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Principal {
    public static void main(String args[]) {
        // Pega o primeiro argumento da linha de comando
        String arquivoSaida = args[0];

        // Cria um objeto para escrever no arquivo
        try(PrintWriter pw = new PrintWriter(arquivoSaida)) {
            pw.print("Imprimindo no arquivo, sem quebra de linha no final");
            pw.println("...Agora imprimindo com quebra de linha");
            pw.println("no final");
        } catch(FileNotFoundException fnfe) {
            System.err.println("O arquivo/diretório não existe:"+args[1]);
        }
    }
}
```

Esse código utiliza o conceito de [try-with-resources](https://www.baeldung.com/java-try-with-resources), que facilita pois encapsula todas as exceções e fechamento dos recursos abertos (um arquivo, no caso), em uma construção única.

Encontrou algum erro ou informação faltando? Faça um pull request para corrigir ou adicionar alguma coisa! Sua contribuição é bem-vinda!