using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Photon;

public class LoadCharacter :  Photon.PunBehaviour
{

    // Start is called before the first frame update
    public GameObject[] characterPrefabs;
    public Transform spawnPoint;
    // public GameObject ThirdPersonControll;

    public string character;

    void Start()
    {
        int selectedCharacter = PlayerPrefs.GetInt("selectedCharacter");

        /*
        character = characterPrefabs[selectedCharacter].ToString();
        Debug.Log("--------------------------------");
        Debug.Log(character);
        // Debug.Log(Resources.Load());
        
        Debug.Log(character.GetType());
        */
        // PhotonNetwork.Instantiate("ThirdPersonController", new Vector3(0,3.0f,0), Quaternion.identity, 0);
        // player = Instantiate(characterPrefabs[selectedCharacter],spawnPoint.position, Quaternion.identity);
        GameObject player = PhotonNetwork.Instantiate(characterPrefabs[selectedCharacter].name,spawnPoint.position, Quaternion.identity,0);
        if(player){
            Debug.Log(player.name);
        }else{
            Debug.Log("error");
        }
        // float x = 0.3F;
        // player.gameObject.transform.localScale = new Vector3(x,x,x);
        player.gameObject.tag = "Player";
        player.gameObject.AddComponent<Player>();
        player.gameObject.AddComponent<SelectItem>();
        // player.gameObject.AddComponent<SelectItem>();
    }
    // Update is called once per frame
    void Update()
    {
    }
}
