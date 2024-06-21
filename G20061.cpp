#include <cassert>
#include <cstdio>
#include <vector>
#include <iostream>
#include <queue>
#include <stack>

#define BOARD_SIZE (4)
#define GREEN_BOARD_ROW_SIZE (6)
#define BLUE_BOARD_COL_SIZE (6)
#define BOUDARY_BOARD_SIZE (2)

class Position
{
public:
	Position(unsigned int row, unsigned int col);

	unsigned int GetRow() const;
	unsigned int GetCol() const;
private:
	unsigned int mRow;
	unsigned int mCol;
};

Position::Position(unsigned int row, unsigned int col)
	: mRow(row)
	, mCol(col)
{
}

unsigned int Position::GetRow() const
{
	return mRow;
}

unsigned int Position::GetCol() const
{
	return mCol;
}

void GetResultOfBlueBoard(bool blueBoard[][BLUE_BOARD_COL_SIZE], const std::vector<Position>& positions, unsigned int* outScore);
void GetResultOfGreenBoard(bool greenBoard[][BOARD_SIZE], const std::vector<Position>& positions, unsigned int* outScore);

void pullRowBlocks(unsigned int row, bool board[][BOARD_SIZE]);
void pullColBlocks(unsigned int col, bool board[][BLUE_BOARD_COL_SIZE]);

int main()
{
	//const unsigned int BOARD_SIZE = 4;
	//const unsigned int BLUE_BOARD_COL_SIZE = 6;
	//const unsigned int GREEN_BOARD_ROW_SIZE = 6;

	//bool board[BOARD_SIZE][BOARD_SIZE];
	bool blueBoard[BOARD_SIZE][BLUE_BOARD_COL_SIZE];
	bool greenBoard[GREEN_BOARD_ROW_SIZE][BOARD_SIZE];
	
	for (unsigned int i = 0; i < GREEN_BOARD_ROW_SIZE; ++i)
	{
		for (unsigned int j = 0; j < BOARD_SIZE; ++j)
		{
			greenBoard[i][j] = false;
			blueBoard[j][i] = false;
		}
	}

	unsigned int blockCount;
	std::cin >> blockCount;
	assert(blockCount <= 10000U);

	unsigned int score = 0;
	std::vector<Position> positions;
	positions.reserve(2);

	for (unsigned int i = 0; i < blockCount; ++i)
	{
		unsigned int blockType;
		unsigned int row;
		unsigned int col;
		std::cin >> blockType >> row >> col;

		switch (blockType)
		{
		case 2:
			positions.push_back(Position(row, col + 1));
			break;
		case 3:
			positions.push_back(Position(row + 1, col));
			break;
		default:
			break;
		}
		positions.push_back(Position(row, col));
		GetResultOfBlueBoard(blueBoard, positions, &score);
		GetResultOfGreenBoard(greenBoard, positions, &score);

		positions.clear();
	}

	unsigned int leftBlocksCount = 0;
	for (unsigned int i = 0; i < BOARD_SIZE; ++i)
	{
		for (unsigned int j = 0; j < BOARD_SIZE; ++j)
		{
			if (greenBoard[i + 2][j])
			{
				++leftBlocksCount;
			}

			if (blueBoard[i][j + 2])
			{
				++leftBlocksCount;
			}
		}
	}

	std::cout << score << std::endl;
	std::cout << leftBlocksCount;
}

void GetResultOfBlueBoard(bool blueBoard[][BLUE_BOARD_COL_SIZE], const std::vector<Position>& positions, unsigned int* outScore)
{
	unsigned int placeCol = BLUE_BOARD_COL_SIZE - 1;
	for (unsigned int blueCol = 1; blueCol < BLUE_BOARD_COL_SIZE; ++blueCol)
	{	
		for (unsigned int positionIndex = 0; positionIndex < positions.size(); ++positionIndex)
		{
			Position position = positions[positionIndex];
			if (blueBoard[position.GetRow()][blueCol])
			{
				placeCol = blueCol - 1;
				goto blue_found_label;
			}
		}
	}

blue_found_label:
	std::stack<int> explodeCols;

	unsigned int lastCol = positions[0].GetCol();
	for (unsigned int positionIndex = 0; positionIndex < positions.size(); ++positionIndex)
	{
		Position position = positions[positionIndex];
		unsigned int newPlaceCol = placeCol + (position.GetCol() - lastCol);
		blueBoard[position.GetRow()][newPlaceCol] = true;

		bool bExplode = true;
		for (unsigned int blueRow = 0; blueRow < BOARD_SIZE; ++blueRow)
		{
			if (blueBoard[blueRow][newPlaceCol] == false)
			{
				bExplode = false;

				break;
			}
		}

		if (bExplode)
		{
			explodeCols.push(newPlaceCol);
			for (unsigned int blueRow = 0; blueRow < BOARD_SIZE; ++blueRow)
			{
				blueBoard[blueRow][newPlaceCol] = false;
			}

			(*outScore)++;
		}

		lastCol = position.GetCol();
	}

	// pull blocks
	while (explodeCols.empty() == false)
	{
		unsigned int explodeCol = explodeCols.top();
		explodeCols.pop();

		for (unsigned int j = explodeCol; j > 0; --j)
		{
			pullColBlocks(j, blueBoard);
		}

		for (unsigned int i = 0; i < BOARD_SIZE; ++i)
		{
			blueBoard[i][0] = false;
		}
	}

	unsigned int boundaryBlockCount = 0;
	// clear boundary
	for (unsigned int j = 0; j < BOUDARY_BOARD_SIZE; ++j)
	{
		for (unsigned int i = 0; i < BOARD_SIZE; ++i)
		{
			if (blueBoard[i][j])
			{
				++boundaryBlockCount;
				break;
			}
		}
	}

	if (boundaryBlockCount > 0)
	{
		for (unsigned int j = BLUE_BOARD_COL_SIZE - 1; j >= BOUDARY_BOARD_SIZE; --j)
		{
			for (unsigned i = 0; i < BOARD_SIZE; ++i)
			{
				blueBoard[i][j] = blueBoard[i][j - boundaryBlockCount];
			}
		}

		for (unsigned int j = 0; j < BOUDARY_BOARD_SIZE; ++j)
		{
			for (unsigned i = 0; i < BOARD_SIZE; ++i)
			{
				blueBoard[i][j] = false;
			}
		}
	}
}


void GetResultOfGreenBoard(bool greenBoard[][BOARD_SIZE], const std::vector<Position>& positions, unsigned int* outScore)
{
	// move
	unsigned int placeRow = GREEN_BOARD_ROW_SIZE - 1;
	for (unsigned int greenRow = 1; greenRow < GREEN_BOARD_ROW_SIZE; ++greenRow)
	{
		for (unsigned int positionIndex = 0; positionIndex < positions.size(); ++positionIndex)
		{
			Position position = positions[positionIndex];
			if (greenBoard[greenRow][position.GetCol()])
			{
				placeRow = greenRow - 1;
				goto green_found_label;
			}
		}
	}

	// insert
green_found_label:
	std::stack<int> explodeRows;

	unsigned int lastRow = positions[0].GetRow();
	for (unsigned int positionIndex = 0; positionIndex < positions.size(); ++positionIndex)
	{
		Position position = positions[positionIndex];
		unsigned int newPlaceRow = placeRow + (position.GetRow() - lastRow);
		greenBoard[newPlaceRow][position.GetCol()] = true;

		bool bExplode = true;
		for (unsigned int greenCol = 0; greenCol < BOARD_SIZE; ++greenCol)
		{
			if (greenBoard[newPlaceRow][greenCol] == false)
			{
				bExplode = false;

				break;
			}
		}

		if (bExplode)
		{
			explodeRows.push(newPlaceRow);
			for (unsigned int greenCol = 0; greenCol < BOARD_SIZE; ++greenCol)
			{
				greenBoard[newPlaceRow][greenCol] = false;
			}

			(*outScore)++;
		}

		lastRow = position.GetRow();
	}

	// pull blocks
	while (explodeRows.empty() == false)
	{
		unsigned int explodeRow = explodeRows.top();
		explodeRows.pop();

		for (unsigned int i = explodeRow; i > 0; --i)
		{
			pullRowBlocks(i, greenBoard);
		}

		for (unsigned int j = 0; j < BOARD_SIZE; ++j)
		{
			greenBoard[0][j] = false;
		}
	}

	unsigned int boundaryBlockCount = 0;
	// clear boundary
	for (unsigned int i = 0; i < BOUDARY_BOARD_SIZE; ++i)
	{
		for (unsigned int j = 0; j < BOARD_SIZE; ++j)
		{
			if (greenBoard[i][j])
			{
				++boundaryBlockCount;
				break;
			}
		}
	}

	if (boundaryBlockCount > 0)
	{
		for (unsigned i = GREEN_BOARD_ROW_SIZE - 1; i >= BOUDARY_BOARD_SIZE; --i)
		{
			for (unsigned int j = 0; j < BOARD_SIZE; ++j)
			{
				greenBoard[i][j] = greenBoard[i - boundaryBlockCount][j];
			}
		}

		for (unsigned i = 0; i < BOUDARY_BOARD_SIZE; ++i)
		{
			for (unsigned int j = 0; j < BOARD_SIZE; ++j)
			{
				greenBoard[i][j] = false;
			}
		}
	}

}

void pullRowBlocks(unsigned int row, bool board[][BOARD_SIZE])
{
	assert(row > 0);

	for (unsigned int j = 0; j < BOARD_SIZE; ++j)
	{
		board[row][j] = board[row - 1][j];
	}
}

void pullColBlocks(unsigned int col, bool board[][BLUE_BOARD_COL_SIZE])
{
	assert(col > 0);

	for (unsigned int i = 0; i < BOARD_SIZE; ++i)
	{
		board[i][col] = board[i][col - 1];
	}
}
