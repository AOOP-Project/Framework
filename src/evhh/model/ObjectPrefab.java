package evhh.model;

import evhh.model.gamecomponents.Sprite;

import java.io.Serializable;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-13
 * @time: 14:31
 **********************************************************************************************************************/
public interface ObjectPrefab extends Serializable
{
    public GameObject getInstance(int x, int y);
    public Sprite getSprite();
}
