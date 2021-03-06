package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 05.01.2018.
 */

public class Powerline extends ElectricActor {

    private Direction direction;

    public Powerline(IVector2 pos, Direction direction) {
        this.pos = pos;
        this.direction = direction;
        maxCapacity = 1;
        speed = 0.5f;
        progress = -1;
    }

    public void update (float dt){
        if(capacity > 0) {
            busy = true;
            progress += dt * speed;
            if (progress >= 1) {
                if(movePowerToElectricActor()){
                    progress = -1;
                }else{
                    progress = 1;
                }
            }
        }else   busy = false;
    }

    @Override
    public void draw(Batch batch, int x, int y, Array<FLayer> fLayers) {
        DrawH.drawActorRot(batch, x,y, direction, image());
        if(capacity > 0) {
            switch (direction) {
                case left:
                    fLayers.add(new FLayer(x - progress, y, 1));
                    break;
                case right:
                    fLayers.add(new FLayer(x + progress, y, 1));
                    break;
                case up:
                    fLayers.add(new FLayer(x, y + progress, 1));
                    break;
                case down:
                    fLayers.add(new FLayer(x, y - progress, 1));
                    break;
            }
        }
    }

    @Override
    public boolean movePowerToElectricActor(){
        ElectricActor actor = (ElectricActor) checkForNearActor();
        if(actor != null){
            if(!actor.isBusy()) {
                actor.addCapacity(1);
                if (actor.getId() == ItemId.POWERLINE) actor.setProgress(0);
                capacity--;
                return true;
            }
        }
        return false;
    }
    private Actor checkForRightActor(IVector2 pos){
        Actor actor = WorldM.getActor(pos);
        if(actor != null) {
            if (actor.getId() == ItemId.ELECTRICOVEN || actor.getId() == ItemId.POWERLINE) return actor;
        }
        return null;
    }

    public Actor checkForNearActor(){
        switch (direction){
            case left:
                return checkForRightActor(new IVector2(pos.x -  1, pos.y));
            case right:
                return checkForRightActor(new IVector2(pos.x + 1, pos.y));
            case up:
                return checkForRightActor(new IVector2(pos.x, pos.y + 1));
            case down:
                return checkForRightActor(new IVector2(pos.x, pos.y - 1));
        }
        return null;
    }

    @Override
    public Collision coll() {return Collision.none;}
    public int image(){return 6;}
    @Override
    public void setProgress(int progress){this.progress = progress;}
    @Override
    public boolean needUpdate(){ return true;}
    public ItemId getId() {
        return ItemId.POWERLINE;
    }

    @Override
    public Direction getDirection(){return direction;}
}
