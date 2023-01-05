package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.Object.GameObject;
import at.ac.fhcampuswien.entity.Entity;

public class CollisionDetection {
    MyPanel panel;
    public CollisionDetection(MyPanel panel){
        this.panel = panel;
    }

    //not Player! Entity
    //use methode to check not only player but also Enemy's, NPCs, ...
    public void DetectTile(Entity entity){
        //need to know Map-position of collision Area (not player position)
        /*Area.x = 8;
        Area.y = 16;
        Area.width = 32;
        Area.height = 32;*/
        int CollisionLeft = entity.MapX + entity.Area.x;
        int CollisionRight = entity.MapX + entity.Area.x + entity.Area.width;
        int CollisionTop = entity.MapY + entity.Area.y;
        int CollisionBottom = entity.MapY + entity.Area.y + entity.Area.height;

        //find coordinates based on tiles   -> Column & Row numbers
        int LeftCol = CollisionLeft/panel.tileSize; //collision in px divided with tileSize
        int RightCol = CollisionRight/panel.tileSize;
        int TopRow = CollisionTop/panel.tileSize;
        int BottomRow = CollisionBottom/panel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "UP" -> {
                TopRow = (CollisionTop - entity.Speed) / panel.tileSize;  //-Speed: predict where player will be next
                tileNum1 = panel.manager.mapTilesNum[LeftCol][TopRow];  //TopLeft Corner of collision
                tileNum2 = panel.manager.mapTilesNum[RightCol][TopRow]; //TopRight Corner of collision
                if (panel.manager.tile[tileNum1].collision || panel.manager.tile[tileNum2].collision)   //if either of the corners (one or both) hit the wall
                    entity.collisionOn = true;
            }
            case "DOWN" -> {
                BottomRow = (CollisionBottom + entity.Speed) / panel.tileSize;
                tileNum1 = panel.manager.mapTilesNum[LeftCol][BottomRow];
                tileNum2 = panel.manager.mapTilesNum[RightCol][BottomRow];
                if (panel.manager.tile[tileNum1].collision || panel.manager.tile[tileNum2].collision)
                    entity.collisionOn = true;
            }
            case "LEFT" -> {
                LeftCol = (CollisionLeft - entity.Speed) / panel.tileSize;
                tileNum1 = panel.manager.mapTilesNum[LeftCol][TopRow];
                tileNum2 = panel.manager.mapTilesNum[LeftCol][BottomRow];
                if (panel.manager.tile[tileNum1].collision || panel.manager.tile[tileNum2].collision)
                    entity.collisionOn = true;
            }
            case "RIGHT" -> {
                RightCol = (CollisionRight + entity.Speed) / panel.tileSize;
                tileNum1 = panel.manager.mapTilesNum[RightCol][TopRow];
                tileNum2 = panel.manager.mapTilesNum[RightCol][BottomRow];
                if (panel.manager.tile[tileNum1].collision || panel.manager.tile[tileNum2].collision)
                    entity.collisionOn = true;
            }
        }
    }

    public int DetectObject(Entity entity, boolean player){
        int index = 999;

        for(int i = 0; i < panel.object.length; i++){

            if(panel.object[i] != null){

                //get entity's collision area position
                entity.Area.x = entity.MapX + entity.Area.x;
                entity.Area.y = entity.MapY + entity.Area.y;

                //get the object's collision area position
                panel.object[i].Area.x = panel.object[i].MapX + panel.object[i].Area.x;
                panel.object[i].Area.y = panel.object[i].MapY + panel.object[i].Area.y;

                switch (entity.direction) {
                    case "UP" -> {
                        entity.Area.y -= entity.Speed;      //when going UP -> Y coordinate - Speed (4 px)


                        //automatically checks if entity.Area & panel.object[i].Area have intersection
                        if (entity.Area.intersects(panel.object[i].Area)) {
                            if (panel.object[i].collision) {  //collision == true
                                entity.collisionOn = true;
                            }
                            if (player) {                     //player == true
                                index = i;                  //if it is player, we return the index, if it is an NPC we ignore
                            }
                        }
                    }
                    case "DOWN" -> {
                        entity.Area.y += entity.Speed;
                        if (entity.Area.intersects(panel.object[i].Area)) {
                            if (panel.object[i].collision) {  //collision == true
                                entity.collisionOn = true;
                            }
                            if (player) {                     //player == true
                                index = i;
                            }
                        }
                    }
                    case "LEFT" -> {
                        entity.Area.x -= entity.Speed;
                        if (entity.Area.intersects(panel.object[i].Area)) {
                            if (panel.object[i].collision) {  //collision == true
                                entity.collisionOn = true;
                            }
                            if (player) {                     //player == true
                                index = i;
                            }
                        }
                    }
                    case "RIGHT" -> {
                        entity.Area.x += entity.Speed;
                        if (entity.Area.intersects(panel.object[i].Area)) {
                            if (panel.object[i].collision) {  //collision == true
                                entity.collisionOn = true;
                            }
                            if (player) {                     //player == true
                                index = i;
                            }
                        }
                    }
                }
                //reset numbers -> otherwise entity.MapX and entity.MapY keep increasing
                entity.Area.x = entity.AreaDefaultX;
                entity.Area.y = entity.AreaDefaultY;
                panel.object[i].Area.x = panel.object[i].AreaDefaultX;
                panel.object[i].Area.y = panel.object[i].AreaDefaultY;
            }
        }
        return index;
    }
}
