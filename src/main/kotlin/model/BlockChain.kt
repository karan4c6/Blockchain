package model

class BlockChain {

    private var blocks = mutableListOf<Block>()

    fun add(block: Block): Block {
        val minedBlock = if (isMined(block)) block else mine(block)
        blocks.add(minedBlock)
        return minedBlock
    }

    private val difficulty = 5
    private val validPrefix = "0".repeat(difficulty)

    private fun isMined(block: Block): Boolean {
        return block.hash.startsWith(validPrefix)
    }

    var UTXO: MutableMap<String, TransactionOutput> = mutableMapOf()

    private fun mine(block: Block): Block {
        println("Mining: $block")
        var minedBlock = block.copy()
        while (!isMined(minedBlock)) {
            minedBlock = minedBlock.copy(nonce = minedBlock.nonce + 1)
        }

        println("Mined : $minedBlock")
        updateUTXO(minedBlock)
        return minedBlock
    }

    private fun updateUTXO(block: Block) {
        // block.transactions.flatMap { it.inputs }.map { it.hash }.forEach { UTXO.remove(it) }
        // UTXO.putAll(block.transactions.flatMap { it.outputs }.associateBy { it.hash })
    }

    fun isValid() = when {
        blocks.isEmpty() -> true
        blocks.size == 1 -> blocks[0].hash == blocks[0].calculateHash()
        else -> {
            for (i in 1 until blocks.size) {
                val previousBlock = blocks[i - 1]
                val currentBlock = blocks[i]
                when {
                    currentBlock.hash != currentBlock.calculateHash() -> false
                    currentBlock.previousHash != previousBlock.calculateHash() -> false
                    !(isMined(previousBlock) && isMined(currentBlock)) -> false
                    else -> true
                }
            }
            true
        }
    }
}