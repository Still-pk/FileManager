package com.Pauls;





class SceneController  {
    private SceneView sceneView = new SceneView();
    private SceneModel sceneModel = new SceneModel();




    void sceneStart() {
          sceneModel.SceneModelStart();
          sceneView.setListModel(sceneModel.getListModel());
          sceneView.setMyTreeModel(sceneModel.getDefaultTreeModel());
          sceneView.sceneViewStart();

          }
    }
