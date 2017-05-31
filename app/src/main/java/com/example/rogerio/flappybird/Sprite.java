package com.example.rogerio.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Sprite {
    public Bitmap origem;
    public Bitmap[][] sprites;
    public int spriteAtualHorizontal, spriteAtualVertical, numSpritesHorizontal, numSpritesVertical;
    public float posX, posY;
    public int tempoControle, tempoDecorrido;

    public Sprite(Bitmap origem, int numSpritesHorizontal, int numSpritesVertical, float posX, float posY, int tempoControle) {
        this.spriteAtualHorizontal = 0;
        this.spriteAtualVertical = 0;
        this.posX = posX;
        this.posY = posY;
        this.tempoControle = tempoControle;
        this.tempoDecorrido = 0;
        this.origem = origem;
        this.numSpritesHorizontal = numSpritesHorizontal;
        this.numSpritesVertical = numSpritesVertical;
        this.sprites = new Bitmap[numSpritesHorizontal][numSpritesVertical];

        //variável para controle de qual posição está sendo cortada da sprite original
        int tamCortadoHorizontal = 0;
        int tamCortadoVertical = 0;

        //variável que armazenará o tamanho de cada uma das sprites com base no número de sprites
        int larguraImagem = origem.getWidth() / numSpritesHorizontal;
        int alturaImagem = origem.getHeight() / numSpritesVertical;

        for (int i = 0; i < numSpritesHorizontal; i++) {
            for (int j = 0; j < numSpritesVertical; j++) {
                //1 - Origem da imagem(sprite original)
                //2 - Posição do eixo X que iniciará o corte
                //3 - Posição do eixo Y que iniciará o corte
                //4 - Quantidade de pixels que serão cortados horizontalmente
                //5 - Quantidade de pixels que serão cortados verticalmente
                sprites[i][j] = Bitmap.createBitmap(origem, tamCortadoHorizontal, tamCortadoVertical, larguraImagem, alturaImagem);

                if (numSpritesHorizontal > 1)
                    tamCortadoHorizontal += larguraImagem;
                if (j == numSpritesVertical - 1 && j != 0) {
                    tamCortadoHorizontal = 0;
                }
            }
            if (numSpritesVertical > 1)
                tamCortadoVertical += alturaImagem;
        }
    }

    public void Desenhar(Canvas canvas, Paint paint, float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        tempoDecorrido++;
        canvas.drawBitmap(sprites[spriteAtualHorizontal][spriteAtualVertical], posX, posY, paint);
        if (tempoDecorrido == tempoControle) {
            tempoDecorrido = 0;
            spriteAtualVertical++;
            if (spriteAtualVertical == numSpritesVertical) {
                spriteAtualVertical = 0;
                spriteAtualHorizontal++;
            }

            if (spriteAtualHorizontal == numSpritesHorizontal) {
                spriteAtualHorizontal = 0;
            }
        }
    }
}
