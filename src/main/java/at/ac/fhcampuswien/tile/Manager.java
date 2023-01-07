package at.ac.fhcampuswien.tile;

import at.ac.fhcampuswien.main.GamePanel;
import at.ac.fhcampuswien.main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

// CLASS CONTENT
/*
    MANAGER CONSTRUCTOR
        - Map Matrix
        - call Tile Image Methode
        - Load Map

    TILE IMAGE
        - List all Tiles

    TILE IMAGE SETUP
        - scale Image for improved rendering performance

    LOAD MAP
        - Go through every Column and Row of Map-Matrix
        - Read Map
        - Enter Map in Map-Matrix

    DRAW MAP
        - Find out Tile Image
        - Find out where to draw on the Screen (X- & Y-Coordinate)
        - Stop Camera at Edge of Map
 */

public class Manager {
    GamePanel panel;                //draw the panel
    public Tile[] tile;             //use tiles  (public for CollisionDetection)
    public int[][] mapTilesNum;     //which tile to use  (public for CollisionDetection)


    // MANAGER CONSTRUCTOR
    public Manager(GamePanel panel){
        this.panel = panel;
        tile = new Tile[50];    //kinds of tiles

        mapTilesNum = new int [panel.maxMapCol][panel.maxMapRow];   //[panel.maxScreenCol][panel.maxScreenRow] -> Map Size as Screen Size
                                                                    //Edit: now maxMapCol/Row -> Map Size
        tileImage();                            //call tileImage()
        loadMap("/Maps/Bedroom.txt");   //call loadMap()
    }

    // TILE IMAGE
    public void tileImage(){
        //same as Player
        setup(0, "WO_LUC", true);
        setup(1, "WO_U", true);
        setup(2, "WO_RUC", true);
        setup(3, "WO_L", true);
        setup(4, "WO_M", true);
        setup(5, "WO_R", true);
        setup(6, "FO_LUC", false);
        setup(7, "FO_U1", false);
        setup(8, "FO_U2", false);
        setup(9, "FO_RUC", false);
        setup(10, "FO_L", false);
        setup(11, "FO_M1", false);
        setup(12, "FO_M2", false);
        setup(13, "FO_R", false);
        setup(14, "FO_LLC", false);
        setup(15, "FO_LM1", false);
        setup(16, "FO_LM2", false);
        setup(17, "FO_D1", false);
        setup(18, "FO_D2", false);
        setup(19, "FO_D3", false);
        setup(20, "FO_RLC", false);
    }

    // TILE IMAGE SETUP
    public void setup(int index, String imageName, boolean collision){
        //scale image so that Graphics2D doesn't have to and draw faster
        Utility utility = new Utility();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/" + imageName + ".png")));
            tile[index].image = utility.scaleImage(tile[index].image, panel.tileSize, panel.tileSize);
            tile[index].collision = collision;  //ask if collision needed or not

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // LOAD MAP
    public void loadMap(String filePath){
        //if you want to load a different Map, just call different loadMap
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
                if(col == panel.maxMapCol){                     //If Column reached the end...
                    col = 0;                                    //... then reset Column back to beginning ...
                    row++;                                      //... but next Row
                }
            }
            bufferedReader.close();     //After Matrix ended -> stop reading

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // DRAW MAP
    public void draw(Graphics2D graphics2D){
        int MapCol = 0;
        int MapRow = 0;

        while(MapCol< panel.maxMapCol && MapRow < panel.maxMapRow)  //instead of writing every tile
        {
            int tileNum = mapTilesNum[MapCol][MapRow];        //Draw number of each read Map-Matrix

            //Needed: 1. Tile image / 2. Where to draw on the screen (find X & Y)
            int MapX = MapCol*panel.tileSize;
            int MapY = MapRow*panel.tileSize;
            int ScreenX = MapX - panel.player.MapX + panel.player.ScreenX; //coordinates to get to a specific tile; compared to the player where is the tile
            int ScreenY = MapY - panel.player.MapY + panel.player.ScreenY;

            // STOP CAMERA AT EDGE
            //Left Side / Top Side
            if(panel.player.ScreenX > panel.player.MapX) ScreenX = MapX;
            if(panel.player.ScreenY > panel.player.MapY) ScreenY = MapY;

            //Right Side / Bottom Side
            int RightOffset = panel.ScreenWidth - panel.player.ScreenX;
            if(RightOffset > panel.MapWidth - panel.player.MapX)
                ScreenX = panel.ScreenWidth - (panel.MapWidth - MapX);

            int BottomOffset = panel.ScreenHeight - panel.player.ScreenY;
            if(BottomOffset > panel.MapHeight - panel.player.MapY)
                ScreenY = panel.ScreenHeight - (panel.MapHeight - MapY);

            if(MapX + panel.tileSize > panel.player.MapX - panel.player.ScreenX
                    && MapX - panel.tileSize < panel.player.MapX + panel.player.ScreenX
                    && MapY + panel.tileSize > panel.player.MapY - panel.player.ScreenY
                    && MapY - panel.tileSize < panel.player.MapY + panel.player.ScreenY)   //create boundary -> only draw tiles within the boundary
                graphics2D.drawImage(tile[tileNum].image, ScreenX, ScreenY, null); //draw

            else if(panel.player.ScreenX > panel.player.MapX
                    || panel.player.ScreenY > panel.player.MapY
                    || RightOffset > panel.MapWidth - panel.player.MapX
                    || BottomOffset > panel.MapHeight - panel.player.MapY)         //if at edge fill Screen with tiles
                graphics2D.drawImage(tile[tileNum].image, ScreenX, ScreenY, null); //draw

            MapCol++;                           //draw next tile
            if(MapCol == panel.maxScreenCol){   //if col finished go to next row and repeat
                MapCol = 0;         //reset MapCol
                MapRow++;           //go to next Row
            }
        }
    }
}
