export default function ProductDetails({ id, name, price, picture }) {

    return (
        <div style={{
            display: "flex",
            justifyContent: "center",
            borderBottom: "1px solid gray"
        }}>
            <div style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center"
            }}>
                <div style={{
                    width: "100%",
                    display: "flex",
                    justifyContent: "space-evenly"
                }}>
                    <p>#{id || 0}</p>
                    <p>${Number(price || 0).toFixed(2)}</p>
                </div>
                <div style={{
                    display: "flex",
                    flexDirection: "row",
                    alignItems: "center",
                    jutifyContent: "space-between" 
                }}>
                    <img style={{
                        width: 40,
                        height: 40,
                        objectFit: "cover",
                        borderRadius: 50,
                    }} src={picture} />
                    <p style={{ paddingLeft: 20 }}>{name || "Desconocido"}</p>
                </div>
            </div>
        </div>
    )

}