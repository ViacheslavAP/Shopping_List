package ru.perelyginva.shoppinglist.data

import ru.perelyginva.shoppinglist.domain.ShopItem
import ru.perelyginva.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    override fun addShopItem(shopItem: ShopItem) {
        // устанавливаем у нашего элемента id и увеличиваем его на один
        //проверяем, какой id содержит элемент
        if (shopItem.id == ShopItem.UNDEFINED_ID){ shopItem.id = autoIncrementId++ }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
       val oldElement = getShopItem(shopItem.id) // находим элемент
        shopList.remove(oldElement)// удаляем элемент
        addShopItem(shopItem)//добавляем новый элемент
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        //ищем элемент по его id
       return shopList.find { it.id == shopItemId } ?:
       throw RuntimeException("element with id $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toMutableList() //возвращаем измененную коллекцию
    }
}