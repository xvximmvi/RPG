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
        - Bedroom Tiles
        - Corridor Tiles
        - Bathroom Tiles
        - Kitchen Tiles

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
    public int[][][] mapTilesNum;     //which tile to use  (public for CollisionDetection)


    // MANAGER CONSTRUCTOR
    public Manager(GamePanel panel){
        this.panel = panel;
        tile = new Tile[70];    //how many kinds of tiles

        mapTilesNum = new int [panel.maxMap][panel.maxMapCol][panel.maxMapRow];     //[panel.maxScreenCol][panel.maxScreenRow] -> Map Size as Screen Size
        //Edit: now maxMapCol/Row -> Map Size

        tileImage();                            //call tileImage()
        loadMap("/Maps/Bedroom.txt", 0);    //call loadMap() of first Map (Bedroom)
        loadMap("/Maps/Corridor.txt",1);    //call loadMap() of second Map (Corridor)
        loadMap("/Maps/Bathroom.txt",2);    //call loadMap() of third Map (Bathroom)

    }

    // TILE IMAGE
    public void tileImage(){
        // BEDROOM
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
        setup(20, "FO_D4", false);
        setup(21, "FO_RLC", false);

        // CORRIDOR
        setup(22, "C_WO_LUC", true);
        setup(23, "C_WO_U", true);
        setup(24, "C_WO_RUC", true);
        setup(25, "C_WO_L", true);
        setup(26, "C_WO_M", true);
        setup(27, "C_WO_R", true);
        setup(28, "C_WO_WLU", true);
        setup(29, "C_WO_WL", true);
        setup(30, "C_WO_WLB", true);
        setup(31, "C_WO_WMU", true);
        setup(32, "C_WO_WM", true);
        setup(33, "C_WO_WMB", true);
        setup(34, "C_WO_WRU", true);
        setup(35, "C_WO_WR", true);
        setup(36, "C_WO_WRB", true);

        // BATHROOM
        setup(37, "B_WO_LUC", true);
        setup(38, "B_WO_U", true);
        setup(39, "B_WO_RUC", true);
        setup(40, "B_WO_L", true);
        setup(41, "B_WO_M", true);
        setup(42, "B_WO_R", true);
        setup(43, "B_WO_LBC", true);
        setup(44, "B_WO_B", true);
        setup(45, "B_WO_RBC", true);
        //Floor (optional; few problems with tiles)
        setup(46, "B_FO_L1", false);
        setup(47, "B_FO_1_M1", false);
        setup(48, "B_FO_1_M2", false);
        setup(49, "B_FO_R1", false);
        setup(50, "B_FO_L2", false);
        setup(51, "B_FO_2_M1", false);
        setup(52, "B_FO_2_M2", false);
        setup(53, "B_FO_R2", false);
        setup(54, "B_FO_LBC", false);
        setup(55, "B_FO_B_M1", false);
        setup(56, "B_FO_B_M2", false);
        setup(57, "B_FO_D1", false);
        setup(58, "B_FO_D2", false);
        setup(59, "B_FO_D3", false);
        setup(60, "B_FO_D4", false);
        setup(61, "B_FO_RBC", false);

        // KITCHEN
    }

    // TILE IMAGE SETUP
    public void setup(int index, String imageName, boolean collision){
        //scale image so that Graphics2D doesn't have to and draw faster
        // Edited afterwards due to space redundancy in code
        Utility utility = new Utility();
        try{
            tile[index] = new Tile();   // Create new Tile
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/FWO/" + imageName + ".png"))); //all tiles are from same Folder -> just name is different
            tile[index].image = utility.scaleImage(tile[index].image, panel.tileSize, panel.tileSize);  //size for every tile the same
            tile[index].collision = collision;  //ask if collision needed or not

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // LOAD MAP
    public void loadMap(String filePath, int Map){
        //if you want to load a different Map, just call different loadMap
        //int Map -> to know in which Map we are (Identify the Map Number)
        try{
            InputStream is = getClass().getResourceAsStream(filePath);   //Input Mapping
            assert is != null;      //means "a passed parameter must not be null": if it is null then the test case fails (Javas suggestion)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));  // Read Input (Map-Text)

            int col = 0;    //Column of Map
            int row = 0;    //Row of Map

            while(col < panel.maxMapCol && row < panel.maxMapRow){    //run while there ar still numbers to read
                String line = bufferedReader.readLine();

                while(col < panel.maxMapCol){
                    String[] numbers = line.split(" ");    //Space " " separates numbers in Map -> go to next Column

                    int num = Integer.parseInt(numbers[col]);   //Number of current Column

                    mapTilesNum[Map][col][row] = num;                //Map-Matrix
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

        while(MapCol < panel.maxMapCol && MapRow < panel.maxMapRow)  //instead of writing every tile
        {
            int tileNum = mapTilesNum[panel.currentMap][MapCol][MapRow];        //Draw number of each read Map-Matrix

            // PLAYER ALWAYS AT CENTER OF SCREEN
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
