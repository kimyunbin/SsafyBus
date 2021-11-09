using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LoadCharacter : MonoBehaviour
{
    // Start is called before the first frame update
    public GameObject[] characterPrefabs;
    public Transform spawnPoint;
    public GameObject player;
    public GameObject ThirdPersonControll;

    void Start()
    {
        int selectedCharacter = PlayerPrefs.GetInt("selectedCharacter");
        player = Instantiate(characterPrefabs[selectedCharacter],spawnPoint.position, Quaternion.identity);
        player.gameObject.transform.SetParent(ThirdPersonControll.gameObject.transform);
        float x = 0.3F;
        player.gameObject.transform.localScale = new Vector3(x,x,x);
        player.gameObject.tag = "Player";
        player.gameObject.AddComponent<Player>();
    }
    // Update is called once per frame
    void Update()
    {
    }
}
