package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Types.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 22.10.2017.
 */
// Wenn Position von item (3, 3) und laufband (4, 3) dann wird das Item auf (3.5, 3) verschoben

public class Foerderband extends Actor {
    private static final float SCHRITT_WEITE = (float) 0.5;

    private int richtung ;
    private ItemActor item;
    private IVector2 pos;
    private float progress = -0.5f;


    public void transfer (){
        switch (richtung) {
            case 1:
                if (WorldM.setItemActor(new IVector2(pos.x + 1, pos.y), item)) {
                    item = null;
                }
                break;
            case 2:
                if (WorldM.setItemActor(new IVector2(pos.x - 1, pos.y), item)) {
                    item = null;
                }
                break;
            case 3:
                if (WorldM.setItemActor(new IVector2(pos.x, pos.y + 1), item)) {
                    item = null;
                }
                break;
            case 4:
                if (WorldM.setItemActor(new IVector2(pos.x, pos.y - 1), item)) {
                    item = null;
                }
                break;
        }
    }

    //1 = Links; 2 = Rechts; 3 = Oben; 4 = Unten
    public Foerderband(int richtung, ItemActor item, IVector2 pos) {
        needUpdate = true;
        this.richtung = richtung;
        this.item = item;
        this.pos = pos;


    }

    public void update (float dt){
        progress += dt /10 ;
        if (progress >= 0.5f ) {
            progress = 0.5f;
            transfer();

        }

    }
    public ItemActor getActor(){
        return item;
    }


    public void draw(Batch batch, int x, int y) {
        if(item != null)
            switch (richtung) {
                case 1: // links
                    DrawH.drawItemActor(batch, x - progress,y , 0);
                    break;
                case 2: // rechts
                     DrawH.drawItemActor(batch, x + progress, y ,0 );
                    break;

                case 3: // oben
                    DrawH.drawItemActor(batch, x, y + progress,0 );
                     break;
                case 4: // unten
                    DrawH.drawItemActor(batch, x, y - progress,0 );
                    break;
        }
    }

    @Override
    public int coll() {
        return 0;
    }
    public int image(){return 0;}

}
