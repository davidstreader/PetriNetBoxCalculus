package com.changenode.plugin;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Drag3DObject extends Application {

        private final Group root = new Group();
        private PerspectiveCamera camera;
        private final double sceneWidth = 800;
        private final double sceneHeight = 600;
    
        private double mousePosX;
        private double mousePosY;
        private double mouseOldX;
        private double mouseOldY;
        private final Rotate rotateX = new Rotate(-20, Rotate.X_AXIS);
        private final Rotate rotateY = new Rotate(-20, Rotate.Y_AXIS);
        
        private volatile boolean isPicking=false;
        private Point3D vecIni, vecPos;
        private double distance;
        private double radius = 30;
      //  private Sphere s;
      private Node n;
     // private Node temp;
      private KeyCode kc = KeyCode.BACK_SPACE; //None
    
        @Override
        public void start(Stage stage) {
            Box floor = new Box(1500, 5, 1500);

            floor.setMaterial(new PhongMaterial(Color.GRAY));
            floor.setTranslateY(150);
            root.getChildren().add(floor);
            final PhongMaterial redMaterial = new PhongMaterial();
            redMaterial.setSpecularColor(Color.ORANGE);
            redMaterial.setDiffuseColor(Color.RED);
            final PhongMaterial blueMaterial = new PhongMaterial();
            blueMaterial.setSpecularColor(Color.CYAN);
            blueMaterial.setDiffuseColor(Color.BLUE);

            Sphere sphere = new Sphere(radius);
            sphere.setMaterial(redMaterial);
            sphere.setTranslateY(-100);
            Text sphereT = new Text("Sphere"); 
            sphereT.setBoundsType(TextBoundsType.VISUAL);
            sphereT.setTranslateY(3); sphereT.setTranslateX(3);
            Group spherey = new Group(); //spherey.setTranslateZ(100);
            spherey.getChildren().addAll(sphere,sphereT);
           
            Box box = new Box(2*radius,2*radius,2*radius);
            box.setMaterial(blueMaterial);
            box.setTranslateY(150);box.setTranslateX(90);
            Text boxT = new Text("Boxy"); 
            boxT.setBoundsType(TextBoundsType.VISUAL);
            boxT.setTranslateY(5);boxT.setTranslateX(5);
            Group boxy = new Group(); boxy.setTranslateZ(-100);
            boxy.getChildren().addAll(box,boxT);
            // root.getChildren().add(box);
            // root.getChildren().add(sphere);
             Group g = new Group();  //press the a key to drag  the group of nodes
           //  g.setTranslateX(3);
            g.getChildren().addAll(boxy, spherey);
            root.getChildren().add(g);
// NOTE depthBuffer true is NEEDED with 3D
            Scene scene = new Scene(root, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
            scene.setFill(Color.web("3d3d3d"));
    
            camera = new PerspectiveCamera(true);
            camera.setVerticalFieldOfView(false);
    
            camera.setNearClip(0.1);
            camera.setFarClip(100000.0);
            camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, -3000));
    
            PointLight light = new PointLight(Color.GAINSBORO);
            root.getChildren().add(light);
            root.getChildren().add(new AmbientLight(Color.WHITE));
            Group cameraG = new Group();  //MoveABLe camera
            cameraG.getChildren().add(camera);
            root.getChildren().add(cameraG);
            scene.setCamera(camera);  // make scene us

            scene.setOnKeyPressed(ke -> {
                kc = ke.getCode();  });
            scene.setOnKeyReleased(ke -> { kc = KeyCode.BACK_SPACE; });
            
            scene.setOnMousePressed((MouseEvent me) -> {
                System.out.println(kc);
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                PickResult pr = me.getPickResult();
                if(pr!=null && pr.getIntersectedNode() != null  ){
                    distance=pr.getIntersectedDistance();
                    n =  pr.getIntersectedNode();
                    if (kc==KeyCode.A && n!=null) {
                        n = n.getParent().getParent();
                        System.out.println("n "+n);
                    } else if (n!=null) {
                        n = n.getParent();
                    }
                    isPicking=true;
                    vecIni = unProjectDirection(mousePosX, mousePosY, scene.getWidth(),scene.getHeight());
                }
            });
            scene.setOnMouseDragged((MouseEvent me) -> {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                if(isPicking){
                    vecPos = unProjectDirection(mousePosX, mousePosY, scene.getWidth(),scene.getHeight());
                    Point3D p=vecPos.subtract(vecIni).multiply(distance);
                     n.getTransforms().add(new Translate(p.getX(),p.getY(),p.getZ()));
                    vecIni=vecPos;
                    PickResult pr = me.getPickResult();
                    if(pr!=null && pr.getIntersectedNode() != null ){  //&& pr.getIntersectedNode()==n
                        distance=pr.getIntersectedDistance();
                    } else {
                        isPicking=false;
                    }
                } else {
                    if (kc==KeyCode.Z ) {
                        camera.getTransforms().add(new Translate(0,0,mousePosX - mouseOldX));
                    } else {
                    rotateX.setAngle(rotateX.getAngle()-(mousePosY - mouseOldY));
                    rotateY.setAngle(rotateY.getAngle()+(mousePosX - mouseOldX));
                    mouseOldX = mousePosX;
                    mouseOldY = mousePosY;
                }}
            });

            scene.setOnMouseReleased((MouseEvent me)->{
                if(isPicking){
                    isPicking=false;
                }
            });
    
            stage.setTitle("3D Dragging");
            stage.setScene(scene);
            stage.show();
        }
    
        /*
         From fx83dfeatures.Camera3D
         http://hg.openjdk.java.net/openjfx/8u-dev/rt/file/5d371a34ddf1/apps/toys/FX8-3DFeatures/src/fx83dfeatures/Camera3D.java
        */
        public Point3D unProjectDirection(double sceneX, double sceneY, double sWidth, double sHeight) {
            double tanHFov = Math.tan(Math.toRadians(camera.getFieldOfView()) * 0.5f);
            Point3D vMouse = new Point3D(tanHFov*(2*sceneX/sWidth-1), tanHFov*(2*sceneY/sWidth-sHeight/sWidth), 1);
    
            Point3D result = localToSceneDirection(vMouse);
            return result.normalize();
        }
    
        public Point3D localToScene(Point3D pt) {
            Point3D res = camera.localToParentTransformProperty().get().transform(pt);
            if (camera.getParent() != null) {
                res = camera.getParent().localToSceneTransformProperty().get().transform(res);
            }
            return res;
        }
    
        public Point3D localToSceneDirection(Point3D dir) {
            Point3D res = localToScene(dir);
            return res.subtract(localToScene(new Point3D(0, 0, 0)));
        }
    
        public static void main(String[] args) {
            launch(args);
        }
    }
        

