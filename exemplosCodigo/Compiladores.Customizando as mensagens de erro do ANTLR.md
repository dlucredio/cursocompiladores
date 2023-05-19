# Construção de Compiladores - Daniel Lucrédio, Helena Caseli, Mário César San Felice e Murilo Naldi
## Customizando as mensagens de erro do ANTLR

O parser gerado pelo ANTLR, por default, imprime as saídas no console. Então, quando acontece algum erro sintático, ele imprime uma mensagem padrão. Para o T1 e T2, vocês vão ter que customizar essas mensagens. Este documento explica a estratégia para conseguir isso.

### Erros léxicos (T1)

Para o T1 (customizando erro léxico), o jeito mais fácil é criar regras "erradas" para capturar os erros como se fossem tokens reais. Assim, na gramática, você pode fazer o seguinte:

```diff
lexer grammar AlgumaLexer;

PALAVRA_CHAVE:
    'DECLARACOES' | 'ALGORITMO' | 'INT' | 'REAL' | 'ATRIBUIR' | 'A' | 'LER' | 'IMPRIMIR' | 'SE' | 'ENTAO' | 'ENQUANTO' | 'INICIO' | 'FIM' | 'E' | 'OU';

fragment
DIGITO: '0'..'9';

NUMINT:
    ('+'|'-')? DIGITO+;

NUMREAL:
    ('+'|'-')? DIGITO+ '.' DIGITO+;

VARIAVEL:
    [a-zA-Z][a-zA-Z0-0]*;

CADEIA:
    '\'' (ESC_SEQ | ~('\n'|'\''|'\\'))* '\'';

fragment
ESC_SEQ:
    '\\\'';

COMENTARIO:
    '%' ~('\n'|'\r')* '\r'? '\n' { skip(); };

WS: (' '|'\t'|'\r'|'\n') { skip(); };

OP_REL	:	'>' | '>=' | '<' | '<=' | '<>' | '='
	;
OP_ARIT	:	'+' | '-' | '*' | '/'
	;
DELIM	:	':'
	;
ABREPAR :	'('
	;
FECHAPAR:	')'
	;

+ CADEIA_NAO_FECHADA: '\'' (ESC_SEQ | ~('\n'|'\''|'\\'))* '\n';
+ ERRO: .;```
```

Note como a regra de cadeia não fechada é uma cadeia igual à correta, porém no final não tem aspas, e sim a quebra de linha, o que não deveria acontecer.

E note como, no final, existe uma regra genérica, chamada ERRO, que captura tudo (`.` é um coringa).

Dessa forma, o ANTLR nunca irá acusar erro, pois, na pior das hipóteses, a regra ERRO irá capturar tudo.

Para reportar o erro como exigido no T1, basta configurar o código principal para imprimir a mensagem e interromper a execução, com esperado. Algo mais ou menos assim:


```diff
package br.ufscar.dc.compiladores.alguma.lexico;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class Principal {
    public static void main(String args[]) {

        try {
            CharStream cs = CharStreams.fromFileName(args[0]);
            AlgumaLexer lex = new AlgumaLexer(cs);

            Token t = null;
            while ((t = lex.nextToken()).getType() != Token.EOF) {
                String nomeToken = AlgumaLexer.VOCABULARY.getDisplayName(t.getType());

+                if(nomeToken.equals("ERRO")) {
+                    System.out.println("Erro na linha "+t.getLine()+": "+t.getText());
+                    break;
+                } else if(nomeToken.equals("CADEIA_NAO_FECHADA")) {
+                    System.out.println("Cadeia não fechada na linha "+t.getLine());
+                    break;
+                } else {
                    System.out.println("<" + nomeToken + "," + t.getText() + ">");
+                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

### Erros sintáticos (T2)

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

Agora precisamos registrar essa classe como um listener para o parser. Tem que ser feito depois de criar o parser. Fica assim:


```java
CharStream cs = CharStreams.fromFileName(args[0]);
AlgumaLexer lexer = new AlgumaLexer(cs);
CommonTokenStream tokens = new CommonTokenStream(lexer);
AlgumaParser parser = new AlgumaParser(tokens);

MyCustomErrorListener mcel = new MyCustomErrorListener();
parser.addErrorListener(mcel);

parser.programa();
```

Fazendo isso, o parser tem DOIS error listeners registrados: o default e o nosso customizado (```mcel```). Por causa disso, você vai ver mensagens duplicadas no console quando executar. Se quiser remover o default e deixar só o nosso, basta chamar ```parser.removeErrorListeners()``` antes de adicionar o nosso.

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

Agora, na criação do parser, precisamos criar esse ```PrintWriter``` e passar para nosso listener. No caso do parser, fica assim:

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