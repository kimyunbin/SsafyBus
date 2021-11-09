using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class CharacterSelection : MonoBehaviour
{
    public GameObject[] characters;
    public int selectedCharacter = 0;
    // Start is called before the first frame update
    public void NextCharacter()
    {
        characters[selectedCharacter].SetActive(false);
        Debug.Log(characters[selectedCharacter].activeSelf);
        selectedCharacter = (selectedCharacter+1) % characters.Length;
        characters[selectedCharacter].SetActive(true);
        Debug.Log(characters[selectedCharacter].activeSelf);
    }
    public void PreviousCharacter()
    {
        characters[selectedCharacter].SetActive(false);
        selectedCharacter--;
        if(selectedCharacter<0)
        {
            selectedCharacter += characters.Length;
        }
        characters[selectedCharacter].SetActive(true);   
    }

    // Update is called once per frame
    public void StartGame()
    {
        PlayerPrefs.SetInt("selectedCharacter", selectedCharacter);
        SceneManager.LoadScene("SampleScene");
    }
}
