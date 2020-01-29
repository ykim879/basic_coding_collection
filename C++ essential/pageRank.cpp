#include<iostream>
#include<vector>
#include<string>
#include<algorithm>
#include <fstream>
#include <regex>
#include<memory>
using namespace std;

class Vertex
{
public:
	vector <shared_ptr<Vertex>> edge;
	vector<shared_ptr<Vertex>> connected;
	string name;
	string html;
	double page_rank;
	Vertex() { page_rank = 1; };
	Vertex(string n) :name(n) { page_rank = 1; };
	Vertex(string n, string h) :name(n), html(h) { page_rank = 1; };
};



class Adjcency_M
{
	vector<shared_ptr<Vertex>> vertexes;
public:
	static double damping;
	static double sumOfD;
	static double sum;

	static void function1(shared_ptr<Vertex> c)
	{
		shared_ptr<Vertex> p = c;
	}
	static void  pageRank(shared_ptr<Vertex> i)
	{
		int count = i->edge.size();
		sum += i->page_rank / count;
	}
	static void return_result(shared_ptr<Vertex> c)
	{
		int count;
		sum = 0;
		double diff = c->page_rank;
		cout << c->name << "(" << c->html << ")" << endl;
		cout << ": previous " << c->page_rank;
		for_each(c->connected.begin(), c->connected.end(), pageRank);
		c->page_rank = (1 - damping) + damping * sum;;
		cout << " current " << c->page_rank;
		diff -= c->page_rank;
		sumOfD += diff;
		cout << " diff " << diff << " sum " << sumOfD;
		cout << endl;
	}

	Adjcency_M()
	{
		sumOfD = 0;
		damping = 0.85;
		ifstream file("PageRank.html");
		if (file.is_open())
		{
			string line;
			int index = 0;
			while (getline(file, line))
			{
				if (regex_match(line, regex("(<title>)(.*)")))
				{
					shared_ptr<Vertex> newVertex(new Vertex);
					string newName;
					smatch match;
					regex rgx1("\"(.*.)(\">)");
					regex rgx2(">(\\w.*)</a>");
					newName.append(line.begin() + 7, line.end() - 8);
					transform(newName.begin(), newName.end(), newName.begin(), tolower);
					cout << "title:" << newName << endl;
					newVertex->name = newName;
					newVertex = pushV(newVertex);
					getline(file, line);
					while (!regex_match(line, regex(".*.(li>)")))
					{
						if (getline(file, line)) {}
						else break;
					}
					while (regex_match(line, regex(".*.(li>)")))
					{
						string tname;
						string thtml;
						const string s = line;
						if (regex_search(s.begin(), s.end(), match, rgx1))
						{
							cout << "first catch: " << match[1] << endl;
							thtml = match[1];
						}
						if (regex_search(s.begin(), s.end(), match, rgx2))
						{
							cout << "second catch: " << match[1] << endl;
							tname = match[1];
						}
						transform(tname.begin(), tname.end(), tname.begin(), tolower);

						shared_ptr<Vertex> newEdge(new Vertex(tname));
						newEdge = pushV(newEdge);
						newEdge->html = thtml;
						newVertex->edge.push_back(newEdge);
						newEdge->connected.push_back(newVertex);
						getline(file, line);
					}
					index++;

				}
			}
			file.close();

		}
		else cout << "PageRank.html does not exist" << endl;

	}
	shared_ptr<Vertex> pushV(shared_ptr<Vertex> v)
	{
		int i;
		if (vertexes.size() == 0)
		{
			vertexes.push_back(v);
			return v;
		}
		for (i = 0; i < vertexes.size(); i++)
		{
			if (vertexes[i]->name == v->name)break;
		}
		if (i == vertexes.size())
		{
			vertexes.push_back(v);
			return v;
		}
		else
		{
			return vertexes[i];
		}


	}

	void result()
	{
		int trial = 1;
		bool result = false;
		while (!result)
		{
			sumOfD = 0;
			cout << trial << "============" << endl;
			for_each(vertexes.begin(), vertexes.end(), &Adjcency_M::return_result);
			if (sumOfD < 0.001) result = true;
			trial++;
		}

	}

};
double Adjcency_M::damping = 0.85;
double Adjcency_M::sum = 0;
double Adjcency_M::sumOfD = 0;

int main()
{
	Adjcency_M m;
	m.result();
	return 0;
}