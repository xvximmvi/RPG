package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.entity.Entity;

// CLASS CONTENT
/*
    COLLISION DETECTION
        - Constructor
        -Receive from Panel

    TILE DETECTION
        - Find Coordinates of Collision Area
        - Check if Tile & Player/NPC Area collide
        - CollisionOn -> used in Player/NPC Class

    OBJECT DETECTION
        - Set index
        - Scan current object's area
        - get entity's and object's collision area position
        - Check if Entity (Player/NPC) and Object are touching (Intersection)
        - Return Index (if Player is Colliding) - if NPC we just stop it from moving there
 */

public class CollisionDetection {
    GamePanel panel;

    //COLLISION DETECTION
    public CollisionDetection(GamePanel panel){
        this.panel = panel;
    }

    // TILE DETECTION
    public void DetectTile(Entity entity){
        /*not Player! Entity
        //use methode to check not only player but also Enemy's, NPCs, ...
        1. need to know Map-position of collision Area (not player position)
        Area.x = 8;
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
                tileNum1 = panel.manager.mapTilesNum[panel.currentMap][LeftCol][TopRow];  //TopLeft Corner of collision
                tileNum2 = panel.manager.mapTilesNum[panel.currentMap][RightCol][TopRow]; //TopRight Corner of collision
                if (panel.manager.tile[tileNum1].collision || panel.manager.tile[tileNum2].collision)   //if either of the corners (one or both) hit the wall
                    entity.collisionOn = true;
            }
            case "DOWN" -> {
                BottomRow = (CollisionBottom + entity.Speed) / panel.tileSize;
                tileNum1 = panel.manager.mapTilesNum[panel.currentMap][LeftCol][BottomRow];
                tileNum2 = panel.manager.mapTilesNum[panel.currentMap][RightCol][BottomRow];
                if (panel.manager.tile[tileNum1].collision || panel.manager.tile[tileNum2].collision)
                    entity.collisionOn = true;
            }
            case "LEFT" -> {
                LeftCol = (CollisionLeft - entity.Speed) / panel.tileSize;
                tileNum1 = panel.manager.mapTilesNum[panel.currentMap][LeftCol][TopRow];
                tileNum2 = panel.manager.mapTilesNum[panel.currentMap][LeftCol][BottomRow];
                if (panel.manager.tile[tileNum1].collision || panel.manager.tile[tileNum2].collision)
                    entity.collisionOn = true;
            }
            case "RIGHT" -> {
                RightCol = (CollisionRight + entity.Speed) / panel.tileSize;
                tileNum1 = panel.manager.mapTilesNum[panel.currentMap][RightCol][TopRow];
                tileNum2 = panel.manager.mapTilesNum[panel.currentMap][RightCol][BottomRow];
                if (panel.manager.tile[tileNum1].collision || panel.manager.tile[tileNum2].collision)
                    entity.collisionOn = true;
            }
        }
    }

    // OBJECT DETECTION
    public int DetectObject(Entity entity, boolean player){     //Player or NPCs
        int index = 999;        //If Player is touching Object -> we return Index (can be any number)

        for(int i = 0; i < panel.object[2].length; i++){   //scan current object's Area
            if(panel.object[panel.currentMap][i] != null){                //Check if it is null -> if not than continue

                //get entity's collision area position
                entity.Area.x = entity.MapX + entity.Area.x;
                entity.Area.y = entity.MapY + entity.Area.y;

                //get the object's collision area position
                panel.object[panel.currentMap][i].Area.x = panel.object[panel.currentMap][i].MapX + panel.object[panel.currentMap][i].Area.x; //no need for coordinates like in TileDetection
                panel.object[panel.currentMap][i].Area.y = panel.object[panel.currentMap][i].MapY + panel.object[panel.currentMap][i].Area.y;

                switch (entity.direction) {
                    case "UP" -> {
                        entity.Area.y -= entity.Speed;      //when going UP -> Y-Coordinate - Speed (4 px)

                        //automatically checks if entity.Area & panel.object[i].Area have intersection
                        if (entity.Area.intersects(panel.object[panel.currentMap][i].Area)) {     //A intersection B
                            if (panel.object[panel.currentMap][i].collision)  //collision == true -> if Object is solid (has collision Area)...
                                entity.collisionOn = true;  //then collision is happening

                            if (player)                     //player == true (player from boolean)
                                index = i;                  //if it is player, we return the index, if it is an NPC we ignore
                        }
                    }
                    case "DOWN" -> {
                        entity.Area.y += entity.Speed;
                        if (entity.Area.intersects(panel.object[panel.currentMap][i].Area)) {
                            if (panel.object[panel.currentMap][i].collision) {  //collision == true
                                entity.collisionOn = true;
                            }
                            if (player) {                     //player == true
                                index = i;
                            }
                        }
                    }
                    case "LEFT" -> {
                        entity.Area.x -= entity.Speed;
                        if (entity.Area.intersects(panel.object[panel.currentMap][i].Area)) {
                            if (panel.object[panel.currentMap][i].collision) {  //collision == true
                                entity.collisionOn = true;
                            }
                            if (player) {                     //player == true
                                index = i;
                            }
                        }
                    }
                    case "RIGHT" -> {
                        entity.Area.x += entity.Speed;
                        if (entity.Area.intersects(panel.object[panel.currentMap][i].Area)) {
                            if (panel.object[panel.currentMap][i].collision) {  //collision == true
                                entity.collisionOn = true;
                            }
                            if (player) {                     //player == true
                                index = i;
                            }
                        }
                    }
                }   /* This methode works better on objects than tiles, because there are to many tiles and just a few objects.
                    If we'd use this method and check every tile for each direction, it would take more time and can cause problems */

                //reset Areas -> otherwise entity.MapX and entity.MapY keep increasing (Line 99 & 100)
                entity.Area.x = entity.AreaDefaultX;
                entity.Area.y = entity.AreaDefaultY;
                panel.object[panel.currentMap][i].Area.x = panel.object[panel.currentMap][i].AreaDefaultX;
                panel.object[panel.currentMap][i].Area.y = panel.object[panel.currentMap][i].AreaDefaultY;
            }
        }
        return index;       //return index
    }
}
