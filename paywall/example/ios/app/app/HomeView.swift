//
//  HomeView.swift
//  app
//
//  Created by Siddhant Misra on 2025-11-18.
//

import SwiftUI

struct HomeView: View {
//    let contents: [ContentData]
    let contentRepo = ContentRepository()
    var body: some View {
        NavigationStack
        {
            VStack{
                ZStack {
                    Color.matherPrimary.ignoresSafeArea(.all)
                }.frame(height:0)
                ScrollView{
                    if let contents = contentRepo.getAll() {
                        ForEach(contents) { content in
                            NavigationLink(destination: ContentView(contentId: content.id)){
                                CardLayoutView(content: content)
                            }
                        }
                    }
                }
                Spacer(minLength: 0)
            }
            .navigationTitle("Home")
        }
    }
}

#Preview {
    HomeView()
}
