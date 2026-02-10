import Paywall
import SwiftUI

struct DecisionStack: View {
    let key: String?
    let value: String?
    var body: some View {
        VStack(alignment: .leading) {
            Text(key ?? "No Key").font(.title3).fontWeight(.bold)
                .foregroundStyle(.white)
            Text(value ?? "No Decision").font(.body).foregroundStyle(.white)
            Divider()
        }
        .padding([.top], 5.0)
    }
}

struct ContentView: View {
    var contentId: String?
    let contentRepository = ContentRepository()
    @State var decision: WallDecision? = nil
    var body: some View {
        Group {
            if let contentId, let content = contentRepository.getById(contentId)
            {
                VStack {
                    ZStack {
                        Color.matherPrimary.ignoresSafeArea(.all)
                        Text("The Paywall App")
                            .foregroundStyle(.white)
                    }.frame(height: 40)
                    ZStack {
                        VStack {
                            if let contentImage = content.imageUrl {
                                AsyncImage(url: URL(string: contentImage)) {
                                    image in
                                    image
                                        .resizable()
                                        .scaledToFit()
                                } placeholder: {
                                    Image(systemName: "photo")
                                        .resizable()
                                        .aspectRatio(contentMode: .fit)
                                        .foregroundColor(
                                            Color("matherSecondary")
                                        )
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
                                    .padding(.leading, 0.0)

                                Text(content.byline)
                                    .font(.footnote)
                                    .multilineTextAlignment(.trailing)
                                    .padding(.top, 6.0)

                                Text(content.plainText)
                                    .font(.body)
                                    .fontWeight(.light)
                                    .foregroundColor(.secondary)
                                    .multilineTextAlignment(.leading)
                                    .padding(.top)
                            }
                            .padding(.horizontal)

                            Divider()
                        }.task {
                            decision = await getDecision(contentId: contentId)
                        }

                        // Paywall Window
                        if decision != nil {
                            VStack {
                                Spacer()
                                ZStack {
                                    Color.matherSecondary.opacity(0.96)
                                        .ignoresSafeArea(.all)
                                    VStack {
                                        Text("Paywall Result")
                                            .font(.title2)
                                            .fontWeight(.medium)
                                            .padding(.top)
                                            .foregroundStyle(.white)
                                        ScrollView {
                                            DecisionStack(
                                                key: "id",
                                                value: decision?.id
                                            )
                                            DecisionStack(
                                                key: "createdAt",
                                                value: decision?.createdAt
                                            )
                                            DecisionStack(
                                                key: "trace",
                                                value: decision?.trace
                                            )
                                            DecisionStack(
                                                key: "context",
                                                value: decision?.context
                                            )
                                            DecisionStack(
                                                key: "inputs",
                                                value: decision?.inputs
                                            )
                                            DecisionStack(
                                                key: "outcome",
                                                value:
                                                    "\(String(describing: decision?.outcome))"
                                            )
                                            DecisionStack(
                                                key: "searchParams",
                                                value: decision?.searchParams
                                            )
                                            DecisionStack(
                                                key: "experimentsCode",
                                                value: decision?.experimentsCode
                                            )
                                            DecisionStack(
                                                key: "paywallScore",
                                                value:
                                                    "\(decision?.paywallScore ?? 0)"
                                            )
                                            DecisionStack(
                                                key: "userProperties",
                                                value: decision?.userProperties
                                            )
                                            DecisionStack(
                                                key: "contentProperties",
                                                value: decision?
                                                    .contentProperties
                                            )
                                        }
                                        .padding(.horizontal, 15.0)
                                    }
                                }.frame(height: 350)
                            }
                        }
                    }
                    Spacer(minLength: 0)
                }
            } else {
                // Fallback view when no contentId or content not found
                VStack {
                    ZStack {
                        Color.matherPrimary.ignoresSafeArea(.all)
                        Text("The Paywall App")
                            .foregroundStyle(.white)
                    }
                    .frame(height: 40)

                    Spacer()
                    Text("No content available")
                        .foregroundStyle(.secondary)
                    Spacer()
                }
            }
        }
    }
}

#Preview {
    ContentView(contentId: "3815161F-8B08-4129-85FE-5FE17E21E533")
}
