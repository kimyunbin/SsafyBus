using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class LoadCharacter :  MonoBehaviour
{

    // Start is called before the first frame update
    public GameObject[] characterPrefabs;
    public Transform spawnPoint;
    public GameObject player;
    public GameObject ThirdPersonControll;

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
        player = Instantiate(characterPrefabs[selectedCharacter],spawnPoint.position, Quaternion.identity);
        // player = PhotonNetwork.Instantiate(characterPrefabs[selectedCharacter].name,spawnPoint.position, Quaternion.identity,0);
// PhotonNetwork.Instantiate("ThirdPersonController", new Vector3(0,3.0f,0), Quaternion.identity, 0);

        player.gameObject.transform.SetParent(ThirdPersonControll.gameObject.transform);
        float x = 0.3F;
        player.gameObject.transform.localScale = new Vector3(x,x,x);
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
