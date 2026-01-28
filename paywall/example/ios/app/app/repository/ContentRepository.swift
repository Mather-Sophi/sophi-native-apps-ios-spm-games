//
//  ContentRepository.swift
//  app
//
//  Created by Siddhant Misra on 2026-01-06.
//

import Foundation

struct ContentRepository {
    var contents: [ContentData] = [
        ContentData(id: "3815161F-8B08-4129-85FE-5FE17E21E533", headline: "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit", byline: "Jhon Doe", plainText: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec est velit, mattis ut massa non, accumsan auctor arcu. Phasellus varius elit id vehicula dapibus. Fusce non lectus porttitor, venenatis augue vel, faucibus dui. Donec aliquam erat nec ante finibus sagittis. Donec euismod nulla eget feugiat consequat. Integer semper pharetra leo, quis scelerisque nulla ullamcorper nec. Proin sed purus augue. Aliquam nec metus dignissim, dignissim diam a, pharetra risus. Quisque luctus sagittis purus, vitae rhoncus purus. Nunc vitae facilisis leo. Mauris eu augue leo. Vestibulum et ante odio. Quisque hendrerit sodales magna. Integer tortor massa, dignissim ac aliquet et, ultricies nec leo.", imageUrl: "https://picsum.photos/800/400?random=1"),
        ContentData(id: "95798D4C-8AE7-4E77-8BE2-271CEA3BEE32", headline: "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit", byline: "Jhon Doe", plainText: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec est velit, mattis ut massa non, accumsan auctor arcu. Phasellus varius elit id vehicula dapibus. Fusce non lectus porttitor, venenatis augue vel, faucibus dui. Donec aliquam erat nec ante finibus sagittis. Donec euismod nulla eget feugiat consequat. Integer semper pharetra leo, quis scelerisque nulla ullamcorper nec. Proin sed purus augue. Aliquam nec metus dignissim, dignissim diam a, pharetra risus. Quisque luctus sagittis purus, vitae rhoncus purus. Nunc vitae facilisis leo. Mauris eu augue leo. Vestibulum et ante odio. Quisque hendrerit sodales magna. Integer tortor massa, dignissim ac aliquet et, ultricies nec leo.", imageUrl: "https://picsum.photos/800/400?random=2"),
        ContentData(id: "5C2DEDC7-A286-461A-A2D0-04654F4F76EF", headline: "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit", byline: "Jhon Doe", plainText: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec est velit, mattis ut massa non, accumsan auctor arcu. Phasellus varius elit id vehicula dapibus. Fusce non lectus porttitor, venenatis augue vel, faucibus dui. Donec aliquam erat nec ante finibus sagittis. Donec euismod nulla eget feugiat consequat. Integer semper pharetra leo, quis scelerisque nulla ullamcorper nec. Proin sed purus augue. Aliquam nec metus dignissim, dignissim diam a, pharetra risus. Quisque luctus sagittis purus, vitae rhoncus purus. Nunc vitae facilisis leo. Mauris eu augue leo. Vestibulum et ante odio. Quisque hendrerit sodales magna. Integer tortor massa, dignissim ac aliquet et, ultricies nec leo.", imageUrl: "https://picsum.photos/800/400?random=3")
        ]
    
    func getById(_ id: String) -> ContentData? {
        var content = contents.first { $0.id == id }
        content?.getDecision()
        return content
    }
    
    func getAll() -> [ContentData]? {
        return contents
    }
}
