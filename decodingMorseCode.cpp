#include <iostream>
#include <fstream>
#include<vector>
#include<bitset>

using namespace std;
/*hashtable:
purpose: save all the hashtable by hard coding*/
class hashtable
{
public:
	vector<char>characters = { 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9','\'','@',':',',','$','=','!','.','?','"' };
	vector<string>morsecode = { ".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--..","-----",".----","..---","...--","....-",".....","-....","--...","---..","----.",".----.",".--.-.","---...","--..--","...-..-","-...-","-.-.--",".-.-.-","..--..",".-..-." };
};

/* BinaryFiles:
purpose: decode Morse.bin*/
class BinaryFiles
{
private:
	unsigned char *binaryC;
	vector<bitset<8>> binaryN;
	vector<bitset<2>> morsecode;
	vector<string> morse;
	int s;

public:

	/*getTheBinaryText
	purpose: get all text texted with morse into binaryC
	binaryC is char* saving all characters in Morse.bin
	It would be decoded by implementDecode function */
	void getTheBinaryText(string sfile)
	{
		unsigned char* memblock = NULL;
		ifstream file(sfile, ios::in | ios::binary);
		if (file.is_open())
		{
			streampos size;
			file.seekg(0, ios::end);
			size = file.tellg();
			s = int(size);
			cout << "The file has " << size << " bytes" << endl;
			memblock = new unsigned char[s];
			file.seekg(0, ios::beg);
			file.read((char*)memblock, size);
			binaryC = memblock;

		}
		else
		{
			cout << "cannot open the file" << endl;
		}
		
	}
	/*setbinaryN()
	implement: it would be implemented in implementDecode function
	purpose: save all the characters(which is saved in binaryC) in binary which is 8 bits */
	void setbinaryN()
	{
		for (int i = 0; i < s; i++)

		{
			int p = binaryC[i];
			if (p < 0) p += 256;
			bitset<8> b(p);
			binaryN.push_back(b);
		}
	}
	/*setMorsecode()
	purpose: set all 8bits binaryN into 2bits morsecode. 
	Later, all morsecode would be convert into LSP||WSP||.||-.
	*/
	void setMorsecode()
	{
		bitset<2> sample;
		cout << "checking morsecode: " << endl;
		for (int i = 0; i < int(binaryN.size()); i++)
		{
			sample[1] = binaryN[i][7];
			sample[0] = binaryN[i][6];
			morsecode.push_back(sample);
			sample[1] = binaryN[i][5];
			sample[0] = binaryN[i][4];
			morsecode.push_back(sample);
			sample[1] = binaryN[i][3];
			sample[0] = binaryN[i][2];
			morsecode.push_back(sample);
			sample[1] = binaryN[i][1];
			sample[0] = binaryN[i][0];
			morsecode.push_back(sample);
		}
	}
	/*setMorse()
	purpose: This will save all morsecode into morse by converting morsecode into string version
	each morse would be one of the hashtable's morsecode.
	Later we could change all morsecode to corrsponding character*/
	void setMorse()
	{
		string s;
		bitset<2> b;
		s.clear();
		for (int i = 0; i < int(morsecode.size()); i++)
		{
			b[0] = morsecode[i][0];
			b[1] = morsecode[i][1];
			if (b.to_string() == "00")
			{
				morse.push_back(s);
				s.clear();
			}
			else if (b.to_string() == "01") s.append("-");
			else if (b.to_string() == "10")s.append(".");
			else if (b.to_string() == "11")
			{
				morse.push_back(s);
				morse.push_back(" ");
			}

			
		}

		
	}
	/*printDecoded()
	purpose: by finding corresponding character we would print out all the deceded test*/
	void printDecoded()
	{
		hashtable h;
		int p = 0;
		while (p < int(morse.size()))
		{
			for (int i = 0; i < int(h.morsecode.size()); i++)
			{
				if (morse[p] == " ")
				{
					cout << " ";
					break;
				}
				else if (morse[p] == h.morsecode[i])
				{
					cout << h.characters[i];
					break;
				}
			}
			p++;
		}
	}
	/*implementDecode()
	purpose: implement all the function which is needed for decoding*/
	void implementDecode()
	{
		setbinaryN();
		setMorsecode();
		setMorse();
		printDecoded();
	}
	~BinaryFiles()
	{
		delete[]binaryC;
	}
};


int main()
{
	BinaryFiles binary1;
	binary1.getTheBinaryText("Morse.bin");
	binary1.implementDecode();
	return 0;
}