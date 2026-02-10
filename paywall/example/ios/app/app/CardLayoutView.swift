import SwiftUI

struct CardLayoutView: View {
    let content: ContentData
    let contentRepo = ContentRepository()
    var body: some View {
        VStack {
            if let contentImage = content.imageUrl {
                AsyncImage(url: URL(string: contentImage)) { image in
                    image
                        .resizable()
                        .scaledToFit()
                } placeholder: {
                    Image(systemName: "photo")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .foregroundColor(Color("matherSecondary"))
                }
            } else {
                Image(systemName: "photo")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .foregroundColor(Color("matherSecondary"))
                    .frame(height: 120)
                    .cornerRadius(10)
            }

            VStack(alignment: .leading) {
                Text(content.headline)
                    .font(.headline)
                    .fontWeight(.medium)
                    .foregroundColor(.primary)
                    .multilineTextAlignment(.leading)
                    .lineLimit(2)
                    .padding(.leading, 0.0)

                Text(content.plainText)
                    .font(.body)
                    .fontWeight(.light)
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.leading)
                    .lineLimit(2)
                    .padding(.top)

                Text(content.byline)
                    .font(.footnote)
                    .multilineTextAlignment(.trailing)
                    .padding(.top, 6.0)

            }
            .padding( /*@START_MENU_TOKEN@*/.horizontal /*@END_MENU_TOKEN@*/)

            Divider()
        }

    }
}

#Preview {
    let data = ContentData(
        id: "ContentID123",
        headline: "Some dummy headline",
        byline: "John Doe",
        plainText: "Some image long text with blah blah blah...",
        imageUrl: "https://picsum.photos/800/400?random=1"
    )
    CardLayoutView(content: data)
}
