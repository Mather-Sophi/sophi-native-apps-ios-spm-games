import SwiftUI

struct HomeView: View {
    let contentRepo = ContentRepository()
    @State var isLoggedIn: Bool = isUserLoggedIn()
    var body: some View {
        NavigationStack {
            VStack {
                ZStack {
                    Color.matherPrimary.ignoresSafeArea(.all)
                }.frame(height: 0)
                Button(action: {
                    if isLoggedIn {
                        logout()
                    } else {
                        login()
                    }
                    isLoggedIn = isUserLoggedIn()

                }) {
                    Image(systemName: "person.fill")
                    Text(isLoggedIn ? "Sign Out" : "Sign In")
                }
                ScrollView {
                    if let contents = contentRepo.getAll() {
                        ForEach(contents) { content in
                            NavigationLink(
                                destination: ContentView(contentId: content.id)
                            ) {
                                CardLayoutView(content: content)
                            }
                        }
                    }
                }
                Spacer(minLength: 0)
            }
        }
    }
}

#Preview {
    HomeView()
}
