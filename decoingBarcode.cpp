#include<iostream>
#include<bitset>
#include<fstream>
#include<vector>
#include<regex>
#include<string>
#include<memory>
#include<list>
#include<algorithm>
#include<thread>
#include<mutex>
#include<chrono>
#include <stdexcept> 
using namespace std;
#define P 32141
mutex mtx;

hash<bitset<48>> ptr;
vector<char> characters{ ' ','-','+','$','%','*','.','/','0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
vector<string> barcode{ "nwwnnnwnn","nwnnnnwnw","nwnnnwnwn","nwnwnwnnn","nnnwnwnwn","nwnnwnwnn","wwnnnnwnn","nwnwnnnwn","nnnwwnwnn","wnnwnnnnw","nnwwnnnnw","wnwwnnnnn","nnnwwnnnw","wnnwwnnnn","nnwwwnnnn","nnnwnnwnw","wnnwnnwnn","nnwwnnwnn","wnnnnwnnw","nnwnnwnnw","wnwnnwnnn",
"nnnnwwnnw","wnnnwwnnn","nnwnwwnnn","nnnnnwwnw","wnnnnwwnn","nnwnnwwnn","nnnnwwwnn","wnnnnnnww","nnwnnnnww","wnwnnnnwn","nnnnwnnww","wnnnwnnwn","nnwnwnnwn","nnnnnnwww","wnnnnnwwn","nnwnnnwwn","nnnnwnwwn","wwnnnnnnw","nwwnnnnnw","wwwnnnnnn","nwnnwnnnw","wwnnwnnnn","nwwnwnnnn" };
class hashTable
{
private:
	vector<char> hash;

public:
	hashTable()
	{
		hash.resize(512);
		for (int i = 0; i < int(characters.size()); i++)
		{
			bitset<9> index(barcode[i], 0, barcode[i].size(), 'n', 'w');
			auto h = hash.begin() + index.to_ulong();
			*h = characters[i];
		}

	}

	char hashing(bitset<9> index)
	{
		auto h_front = hash.begin() + index.to_ulong();
		char result = *h_front;
		return result;
	}
	string checkProduct(bitset<48> b)
	{
		string productName = "-----";
		for (int i = 4; i >= 0; i--)
		{

			bitset<9> temp;
			for (int t = 8; t >= 0; t--)
			{

				temp[t] = b[i * 9 + 3 + t];
			}

			productName[4 - i] = hashing(temp);
		}
		return productName;
	}
	~hashTable(){}
};

class Product
{
public:
	 bitset<48> barcode;
	 vector<double> price;
	 vector<string> n_product;
	Product()
	{
		ifstream barcodeFile;
		barcodeFile.open("ProductPrice.xml");
		if (barcodeFile)
		{
			bitset<48> name;
			double p;
			string line;
			string pname;
			string XMLtext;
			hashTable h;
			while (getline(barcodeFile, line))
			{
				XMLtext.append(line);
			}
			regex Regex("[0-9\.]+");
			regex NRegex("[0-1]{48}");
			auto words_begin = sregex_iterator(XMLtext.begin(), XMLtext.end(), Regex);
			auto words_end = sregex_iterator();
			for (sregex_iterator i = words_begin; i != words_end; ++i)
			{

				smatch match = *i;
				if (regex_match(match.str(), NRegex))
				{
					name = bitset<48>(match.str());
					pname = h.checkProduct(name);
					i++;
					smatch nmatch = *i;
					p = stod(nmatch.str());
					price.resize(P);
					n_product.resize(P);
					barcode = name;
					price.at(ptr(name) % P) = p;
					n_product.at(ptr(name) % P) = pname;
				}

			}
			barcodeFile.close();

		}
		else cout << "ProductPrice.xml does not exist\nCannot conduct ProductList" << endl;
	}
	~Product(){}
};

class Cart
{
public:
	vector<bitset<48>> C_barcode;
	string cartName;

	Cart()
	{
		
	}

};
class Carts
{
public:
	vector<Cart> clist;

	Carts()//for each cart move into next node//make a regex for each , //put right bitset for each character.
	{
		
		ifstream cartFile;
		cartFile.open("Carts.csv");
		if (cartFile)
		{
			string line;
			while (getline(cartFile, line))
			{
				if (regex_match(line, regex("(Cart)(.*)")))
				{
					shared_ptr<Cart> newnode(new Cart());
					newnode->cartName = line;
					getline(cartFile, line);
					regex Regex("[0-9 A-Q]{12}");
					auto words_begin = sregex_iterator(line.begin(), line.end(), Regex);
					auto words_end = sregex_iterator();

					for (sregex_iterator i = words_begin; i != words_end; ++i)
					{

						smatch match = *i;
						match.str().insert(0, "0x");
						string::size_type sz = 0;
						long long value = stoll(match.str(), &sz, 16);
						newnode->C_barcode.push_back(value);
					}

					clist.push_back(*newnode);
				}

			}
			cartFile.close();
		}
		else cout << "Carts.csv does not exist" << endl;
	}




};

void readCart(int laneNum, vector<Cart> here,Product pl,int f)
{

	mtx.lock();
	cout << "Here is a Lane" << laneNum << " :" << endl;
	cout << here[f].cartName << ":" << endl;
	for (int i = 0; i < int(here[f].C_barcode.size()); i++)
	{
		cout << "Product: " << pl.price.at(ptr(here[f].C_barcode[i]) % P) << " ";
		cout << "Name: " << pl.n_product.at(ptr(here[f].C_barcode[i]) % P) << endl;
	}
	mtx.unlock();
}

class Lane
{

public:
#pragma warning (disable: 9000)
	Lane()
	{
		int f = 0;
		Product pl;
		Carts c;
		

			for (int i = 0; i <= int(c.clist.size() / 5); i++)
			{

				thread lane1(readCart, 1, c.clist, pl, f);
			
				if (f==(c.clist.size()-1)) {
					lane1.join(); break;
				}
				f++;
				thread lane2(readCart, 2, c.clist, pl,  f);
				if (f == (c.clist.size() - 1)) {
					lane1.join(); lane2.join();  break;
				}
				f++;
				thread lane3(readCart, 3, c.clist, pl,  f);
				
				if (f == (c.clist.size()-1)) {
					lane1.join(); lane2.join(); lane3.join();  break;
				}
				f++;
				thread lane4(readCart, 4, c.clist, pl, f);
			
				if (f == c.clist.size()-1) {
					lane1.join(); lane2.join(); lane3.join(); lane4.join(); break;
				}
				f++;
				thread lane5(readCart, 5, c.clist, pl,  f);
				
				lane1.join();
				lane2.join();
				lane3.join();
				lane4.join();
				lane5.join();
				if (f == (c.clist.size() - 1)) {
					 break;
				}

				f++;
			}
			

		
	}
	~Lane(){}

};

int main()
{
	
	Lane lanes;

	return 0;
}