package at.ac.fhcampuswien.tile;


import at.ac.fhcampuswien.main.MyPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class Manager {
    MyPanel panel;          //draw the panel
    public Tile[] tile;            //use tiles  (public for CollisionDetection)
    public int[][] mapTilesNum;    //which tile to use  (public for CollisionDetection)

    public Manager(MyPanel panel){
        this.panel = panel;
        tile = new Tile[50];    //kinds of tiles

        mapTilesNum = new int [panel.maxMapCol][panel.maxMapRow];   //[panel.maxScreenCol][panel.maxScreenRow] -> Map Size as Screen Size
                                                                    //Edit: now maxMapCol/Row -> Map Size
        tileImage();            //call tileImage()
        loadMap("/Maps/Bedroom.txt");  //call loadMap()
    }

    public void tileImage(){        //same as Player
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/WO_LUC.png")));
            tile[0].collision = true;       //only need to mark tiles that need collision! other automatically false
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/WO_U.png")));
            tile[1].collision = true;
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/WO_RUC.png")));
            tile[2].collision = true;
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/WO_L.png")));
            tile[3].collision = true;
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/WO_M.png")));
            tile[4].collision = true;
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/WO_R.png")));
            tile[5].collision = true;
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_LUC.png")));
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_U1.png")));
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_U2.png")));
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_RUC.png")));
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_L.png")));
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_M1.png")));
            tile[12] = new Tile();
            tile[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_M2.png")));
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_R.png")));
            tile[14] = new Tile();
            tile[14].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_LLC.png")));
            tile[15] = new Tile();
            tile[15].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_LM1.png")));
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_LM2.png")));
            tile[17] = new Tile();
            tile[17].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_D1.png")));
            tile[18] = new Tile();
            tile[18].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_D2.png")));
            tile[19] = new Tile();
            tile[19].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_D3.png")));
            tile[20] = new Tile();
            tile[20].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/FO_RLC.png")));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){   //if you want to load a different Map, just call different loadMap
        try{
            InputStream is = getClass().getResourceAsStream(filePath);   //Input Mapping
            assert is != null;      //means "a passed parameter must not be null": if it is null then the test case fails
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));  // Read Input (Map-Text)

            int col = 0;    //Column of Map
            int row = 0;    //Row of Map

            while(col < panel.maxMapCol && row < panel.maxMapRow){    //run while there ar still numbers to read
                String line = bufferedReader.readLine();

                while(col < panel.maxMapCol){
                    String[] numbers = line.split(" ");    //Space " " separates numbers in Map -> go to next Column

                    int num = Integer.parseInt(numbers[col]);   //Number of current Column

                    mapTilesNum[col][row] = num;                //Map-Matrix
                    col++;                                      //Go to next Column
                }
                if(col == panel.maxMapCol){                  //If Column reached the end...
                    col = 0;                                    //... then reset Column back to beginning ...
                    row++;                                      //... but next Row
                }
            }
            bufferedReader.close();     //After Matrix ended -> stop reading

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D){
        int MapCol = 0;
        int MapRow = 0;
        //int x = 0;
        //int y = 0;

        while(MapCol< panel.maxMapCol && MapRow < panel.maxMapRow)  //instead of writing every tile
        {
            int tileNum = mapTilesNum[MapCol][MapRow];        //Draw number of each read Map-Matrix

            //Needed: 1. Tile image / 2. Where to draw on the screen (find X & Y)
            int MapX = MapCol*panel.tileSize;
            int MapY = MapRow*panel.tileSize;
            int ScreenX = MapX - panel.player.MapX + panel.player.ScreenX; //coordinates to get to a specific tile; compared to the player where is the tile
            int ScreenY = MapY - panel.player.MapY + panel.player.ScreenY;

            //Stop moving Screen at the edge
            if(panel.player.ScreenX > panel.player.MapX) ScreenX = MapX;
            if(panel.player.ScreenY > panel.player.MapY) ScreenY = MapY;

            int RightOffset = panel.ScreenWidth - panel.player.ScreenX;
            if(RightOffset > panel.MapWidth - panel.player.MapX)
                ScreenX = panel.ScreenWidth - (panel.MapWidth - MapX);

            int BottomOffset = panel.ScreenHeight - panel.player.ScreenY;
            if(BottomOffset > panel.MapHeight - panel.player.MapY)
                ScreenY = panel.ScreenHeight - (panel.MapHeight - MapY);

            if(MapX + panel.tileSize > panel.player.MapX - panel.player.ScreenX
                    && MapX - panel.tileSize < panel.player.MapX + panel.player.ScreenX
                    && MapY + panel.tileSize > panel.player.MapY - panel.player.ScreenY
                    && MapY - panel.tileSize < panel.player.MapY + panel.player.ScreenY) {   //create boundary -> only draw tiles within the boundary
                graphics2D.drawImage(tile[tileNum].image, ScreenX, ScreenY, panel.tileSize, panel.tileSize, null); //draw
            }
            else if(panel.player.ScreenX > panel.player.MapX
                    || panel.player.ScreenY > panel.player.MapY
                    || RightOffset > panel.MapWidth - panel.player.MapX
                    || BottomOffset > panel.MapHeight - panel.player.MapY) {        //if at edge fill Screen with tiles
                graphics2D.drawImage(tile[tileNum].image, ScreenX, ScreenY, panel.tileSize, panel.tileSize, null); //draw
            }
            MapCol++;                  //draw next tile
            //x += panel.tileSize;    //next block
            if(MapCol == panel.maxScreenCol){  //if col finished go to next row and repeat
                MapCol = 0;
               // x = 0;
                MapRow++;
                //y += panel.tileSize;
            }
        }
    }
}
