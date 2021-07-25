package main

import model.Block
import model.BlockChain

fun main() {
    val genesisBlock = Block(previousHash = "0", data = "First Data")
    val secondBlock = Block(previousHash = genesisBlock.hash, data = "Second Block Data")
    val thirdBlock = Block(previousHash = secondBlock.hash, data = "Third Block  Data")

    println(genesisBlock)
    println(secondBlock)
    println(thirdBlock)

    val blockChain = BlockChain()
    blockChain.add(genesisBlock)
    blockChain.add(secondBlock)
    blockChain.add(thirdBlock)
}