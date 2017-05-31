package com.example.rogerio.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Controle extends SurfaceView implements Runnable {
    /////////////////////////////////////////DECLARAÇÃO VARIÁVEIS////////////////////////////////////////////
    //Variável que verifica se o jogo deve estar em loop(executando)
    public boolean rodando;

    //Objeto para verificar se a tela está pronta para receber eventos de desenho
    public SurfaceHolder surfaceHolder;

    //Objeto para iniciar o Loop da classe Runnable
    public Thread thread;

    public Bitmap fundo, chao;

    public Paint paint;

    public Canvas canvas;

    public float escalaX, escalaY, largTela, altTela, largFundo, altFundo, altChao;

    public float posIniFundo, posIniChao;

    public Sprite passaro, explosao;

    public Bitmap flappySprites, explosaoSprites;

    /////////////////////////////////////////FIM DECLARAÇÃO VARIÁVEIS////////////////////////////////////////////
    public Controle(Context context) {
        super(context);
        surfaceHolder = getHolder();
        rodando = false;
        paint = new Paint();
        //recuperamos a imagem a partir da classe R (Resources) e atribuímos a um objeto de imagem (bitmap)
        fundo = BitmapFactory.decodeResource(getResources(), R.drawable.fundoflappy);
        chao = BitmapFactory.decodeResource(getResources(), R.drawable.chaoflappy);
        posIniFundo = 0;
        posIniChao = 0;

        flappySprites = BitmapFactory.decodeResource(getResources(), R.drawable.flappysprites);
        explosaoSprites = BitmapFactory.decodeResource(getResources(),R.drawable.explosion);
    }//fim construtor

    public void iniciar() {
        rodando = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (rodando) {

            if (surfaceHolder.getSurface().isValid()) {

                canvas = surfaceHolder.lockCanvas();

                largFundo = fundo.getWidth();
                altFundo = fundo.getHeight();

                if (passaro == null)
                    passaro = new Sprite(flappySprites, 3, 1, largFundo / 2, altFundo / 2, 1);


                largTela = getWidth();
                altTela = getHeight();

                escalaX = largTela / largFundo;
                escalaY = altTela / altFundo;

                int savedState = canvas.save();

                canvas.scale(escalaX, escalaY);

                altChao = chao.getHeight();

                canvas.drawBitmap(fundo, posIniFundo, 0, paint);

                //para pintar a segunda imagem, verificamos se a posição inicial do fundo atingiu um
                // valor negativo. se atingiu, vamos pintar a segunda imagem
                if (posIniFundo < 0) {
                    canvas.drawBitmap(fundo, largFundo + posIniFundo, 0, paint);
                }
                if (posIniFundo == -largFundo) {
                    posIniFundo = 0;
                }

                canvas.drawBitmap(chao, posIniChao, altFundo - altChao, paint);

                if (posIniChao < 0) {
                    canvas.drawBitmap(chao, posIniChao + chao.getWidth(), altFundo - altChao, paint);
                }
                if (posIniChao == -chao.getWidth()) {
                    posIniChao = 0;
                }

                //Desenhar o pássaro na tela
                passaro.Desenhar(canvas, paint, passaro.posX, passaro.posY);

                //Desenhar a explosão na tela
                //explosao.Desenhar(canvas,paint,explosao.posX,explosao.posY);

                //retorna ao status original da tela
                canvas.restoreToCount(savedState);

                //Ao final, libera o canvas e exibe ao usuário
                surfaceHolder.unlockCanvasAndPost(canvas);

                //A cada laço do loop, estaremos diminuindo meio pixel na posição da imagem de fundo
                posIniFundo = posIniFundo - 10f;
                posIniChao = posIniChao - 10f;
            }
        }
    }
}
