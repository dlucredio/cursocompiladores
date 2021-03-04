# Construção de Compiladores - Daniel Lucrédio, Helena Caseli, Mário César San Felice e Murilo Naldi
## Customizando as mensagens de erro do ANTLR

O parser gerado pelo ANTLR, por default, imprime as saídas no console. Então, quando acontece algum erro sintático, ele imprime uma mensagem padrão. Para o T2, vocês vão ter que customizar essas mensagens. Este documento explica a estratégia para conseguir isso.

O primeiro passo é criar uma classe para receber as notificações de erros gerados pelo parser. Basta criar uma classe nova, que implementa a interface [ANTLRErrorListener](https://www.antlr.org/api/Java/org/antlr/v4/runtime/ANTLRErrorListener.html).

```java

package <seu pacote aqui>;

import org.antlr.v4.runtime.ANTLRErrorListener; // cuidado para importar a versão 4
import org.antlr.v4.runtime.Token; // Vamos também precisar de Token
// Outros imports vão ser necessários aqui. O NetBeans ou IntelliJ fazem isso automaticamente

public class MyCustomErrorListener implements ANTLRErrorListener {
    @Override
    public void	reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }
    
    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }

    @Override
    public void	syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        // Aqui vamos colocar o tratamento de erro customizado

        Token t = (Token) offendingSymbol;

        System.out.println("Minha mensagem customizada: Erro na linha "+line+", o token é "+t.getText());
    }
}
```

Agora precisamos registrar essa classe como um listener para o parser. Tem que ser feito depois de criar o parser, mas antes de chamar a regra inicial, assim:

```java
CharStream cs = CharStreams.fromFileName(args[0]);
AlgumaLexer lexer = new AlgumaLexer(cs);
CommonTokenStream tokens = new CommonTokenStream(lexer);
AlgumaParser parser = new AlgumaParser(tokens);

// Registrar o error lister personalizado aqui
MyCustomErrorListener mcel = new MyCustomErrorListener();
parser.addErrorListener(mcel);

parser.programa();
```

Agora, o parser tem DOIS error listeners registrados: o default, e o nosso customizado (```mcel```). Por causa disso, você vai ver mensagens duplicadas no console quando executar. Se quiser remover o default e deixar só o nosso, basta chamar ```parser.removeErrorListeners()``` antes de adicionar o nosso.

Finalmente, precisamos fazer o error listener imprimir as mensagens no arquivo de saída, e não no console. Tem várias formas de fazer isso. Uma delas é passar um objeto do tipo ```PrintWriter``` para o nosso error listener, via construtor. Assim:

```diff

package <seu pacote aqui>;

import org.antlr.v4.runtime.ANTLRErrorListener; // cuidado para importar a versão 4
import org.antlr.v4.runtime.Token; // Vamos também precisar de Token
// Outros imports vão ser necessários aqui. O NetBeans ou IntelliJ fazem isso automaticamente

public class MyCustomErrorListener implements ANTLRErrorListener {
+   PrintWriter pw;
+   public MyCustomErrorListener(PrintWriter pw) {
+       this.pw = pw;    
+   }

    @Override
    public void	reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }
    
    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        // Não será necessário para o T2, pode deixar vazio
    }

    @Override
    public void	syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        // Aqui vamos colocar o tratamento de erro customizado

        Token t = (Token) offendingSymbol;

-        System.out.println("Minha mensagem customizada: Erro na linha "+line+", o token é "+t.getText());
+        pw.println("Minha mensagem customizada: Erro na linha "+line+", o token é "+t.getText());
    }
}
```

Agora, na criação do parser, precisamos criar esse ```PrintWriter``` e passar para nosso listener:

```diff
+ // Lembrando: args[1] é o arquivo de saída
+ try(PrintWriter pw = new PrintWriter(new File(args[1]))) { 
    CharStream cs = CharStreams.fromFileName(args[0]);
    AlgumaLexer lexer = new AlgumaLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AlgumaParser parser = new AlgumaParser(tokens);

    // Registrar o error lister personalizado aqui
-   MyCustomErrorListener mcel = new MyCustomErrorListener();
+   MyCustomErrorListener mcel = new MyCustomErrorListener(pw);
    parser.addErrorListener(mcel);

    parser.programa();
+ }
```

Esse bloco ```try() {}``` é usado sempre que precisamos usar recursos que precisam ser fechados no final, como escrita de arquivos. Dentro dos parêntesis inicializamos os recursos e eles serão fechados automaticamente no final do bloco. [Aqui tem a explicação oficial](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html).

Para passar nos casos de teste, basta ajustar as mensagens para que saiam exatamente iguais ao esperado e pronto!

Encontrou algum erro ou informação faltando? Faça um pull request para corrigir ou adicionar alguma coisa! Sua contribuição é bem-vinda!